package io.gig.realestate.admin.controller.attachment;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.attachment.AttachmentService;
import io.gig.realestate.domain.attachment.dto.AttachmentCreateForm;
import io.gig.realestate.domain.attachment.dto.AttachmentDto;
import io.gig.realestate.domain.attachment.types.FileType;
import io.gig.realestate.domain.attachment.types.UsageType;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@RestController
@RequestMapping("attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentFacade attachmentFacade;

    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity<ApiResponse> upload(
            @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam(value = "files", required = false) MultipartFile[] multipartFiles,
            @RequestParam("fileType") FileType fileType,
            @RequestParam("usageType") UsageType usageType
    ) {

        AttachmentCreateForm createForm = AttachmentCreateForm.builder()
                .multipartFile(multipartFile)
                .multipartFiles(multipartFiles)
                .usageType(usageType)
                .fileType(fileType)
                .build();

        List<AttachmentDto> response = attachmentFacade.upload(createForm);

        return new ResponseEntity<>(ApiResponse.OK(response), HttpStatus.OK);
    }
}
