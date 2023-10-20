package io.gig.realestate.domain.attachment.dto;

import io.gig.realestate.domain.attachment.types.FileType;
import io.gig.realestate.domain.attachment.types.UsageType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Getter
@Builder
public class AttachmentCreateForm {

    private MultipartFile multipartFile;
    private MultipartFile[] multipartFiles;
    private FileType fileType;
    private UsageType usageType;
}
