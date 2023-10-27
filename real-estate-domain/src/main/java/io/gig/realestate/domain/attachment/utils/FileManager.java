package io.gig.realestate.domain.attachment.utils;

import io.gig.realestate.domain.attachment.types.FileType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
@Component
public class FileManager {
    /* Not Allowed File Extension */
    public static final String FILE_PERMIT_EXTENSION = "hwp,pdf,xls,xlsx,doc,docx,ppt,pptx,zip,alz,jpg,jpeg,png,txt,log,gif";

    /* Image File Extension */
    public static final String IMAGE_FILE_EXTENSION = "bmp,jpg,jpeg,gif,png,psd,ai,pic";

    /* Audio File Extension */
    public static final String VIDEO_FILE_EXTENSION = "mp3,mp4,ogg,wma,wav,au,rm,mid";

    public static boolean permitExtensionCheck(String originalFileName) {
        if (!StringUtils.hasText(originalFileName)) return false;

        String fileExtension = FilenameUtils.getExtension(originalFileName);

        if (Arrays.asList(StringUtils.commaDelimitedListToStringArray(FILE_PERMIT_EXTENSION.trim().toLowerCase())).contains(fileExtension.trim().toLowerCase())) {
            return true;
        }

        if (Arrays.asList(StringUtils.commaDelimitedListToStringArray(VIDEO_FILE_EXTENSION.trim().toLowerCase())).contains(fileExtension.trim().toLowerCase())) {
            return true;
        }

        return false;
    }

    public static FileType defineMediaType(String fileExtension) {
        FileType fileType = FileType.Document;

        if (Arrays.asList(StringUtils.commaDelimitedListToStringArray(IMAGE_FILE_EXTENSION.trim().toLowerCase())).contains(fileExtension.trim().toLowerCase())) {
            fileType = FileType.Image;
        }

        if (Arrays.asList(StringUtils.commaDelimitedListToStringArray(VIDEO_FILE_EXTENSION.trim().toLowerCase())).contains(fileExtension.trim().toLowerCase())) {
            fileType = FileType.Video;
        }

        return fileType;
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile file) throws Exception {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try {
                FileOutputStream fos = new FileOutputStream(convertFile);
                fos.write(file.getBytes());
            } catch (IOException ioe) {
                throw new Exception(ioe);
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
