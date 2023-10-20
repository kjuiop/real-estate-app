package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.dto.AttachmentCreateForm;
import io.gig.realestate.domain.attachment.dto.AttachmentDto;
import io.gig.realestate.domain.attachment.types.FileType;
import io.gig.realestate.domain.attachment.utils.FileManager;
import io.gig.realestate.domain.attachment.utils.S3UploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
@Service
@RequiredArgsConstructor
public class ImageUploadService implements UploadService {

    private final FileManager fileManager;
    private final S3UploadUtils s3UploadUtils;

    @Override
    @Transactional
    public List<AttachmentDto> upload(AttachmentCreateForm request) {

        if (request.getMultipartFile() != null) {
            MultipartFile[] multipartFiles = request.getMultipartFiles();
            multipartFiles[0] = request.getMultipartFile();
        }

        List<AttachmentDto> attachmentDtoList = new ArrayList<>();
        for (MultipartFile mf : request.getMultipartFiles()) {
            String filePath = request.getUsageType().getType() + File.separator +  request.getFileType();

            long time = System.currentTimeMillis();
            String originalFilename = mf.getOriginalFilename();
            String saveFileName = String.format("%d_%s", time, originalFilename.replaceAll(" ", ""));

            File uploadFile = null;
            try {
                Optional<File> uploadFileOpt = fileManager.convertMultipartFileToFile(mf);
                if (uploadFileOpt.isEmpty()) {
                    throw new Exception("파일변환에 실패했습니다.");
                }
                uploadFile = uploadFileOpt.get();
                String saveFilePath = s3UploadUtils.upload(uploadFile, filePath, saveFileName);

                AttachmentDto dto = AttachmentDto.builder()
                        .fileType(FileType.Image)
                        .usageType(request.getUsageType())
                        .originalFilename(originalFilename)
                        .savedFilename(saveFileName)
                        .fullPath(saveFilePath)
                        .build();

                attachmentDtoList.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }  finally {
                if (uploadFile != null) {
                    uploadFile.delete();
                }
            }
        }

        return attachmentDtoList;
    }
}
