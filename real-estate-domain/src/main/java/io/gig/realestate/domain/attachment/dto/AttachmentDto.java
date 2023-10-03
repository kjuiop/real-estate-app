package io.gig.realestate.domain.attachment.dto;

import io.gig.realestate.domain.attachment.Attachment;
import io.gig.realestate.domain.attachment.types.FileType;
import io.gig.realestate.domain.attachment.types.UsageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class AttachmentDto {

    private FileType fileType;

    private UsageType usageType;

    private String originalFilename;

    private String savedFilename;

    private String fullPath;

    public AttachmentDto(Attachment a) {
        this.fileType = a.getFileType();
        this.usageType = a.getUsageType();
        this.originalFilename = a.getOrgFilename();
        this.savedFilename = a.getSavedFilename();
        this.fullPath = a.getFullPath();
    }
}
