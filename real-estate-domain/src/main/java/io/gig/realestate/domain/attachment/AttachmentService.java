package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.dto.AttachmentDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
public interface AttachmentService {
    void create(List<AttachmentDto> result);
}
