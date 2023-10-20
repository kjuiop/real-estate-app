package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.dto.AttachmentCreateForm;
import io.gig.realestate.domain.attachment.dto.AttachmentDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
@FunctionalInterface
public interface UploadService {
    List<AttachmentDto> upload(AttachmentCreateForm request);
}
