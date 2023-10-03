package io.gig.realestate.admin.controller.attachment;

import io.gig.realestate.domain.attachment.AttachmentService;
import io.gig.realestate.domain.attachment.UploadService;
import io.gig.realestate.domain.attachment.UploadServiceFactory;
import io.gig.realestate.domain.attachment.dto.AttachmentCreateForm;
import io.gig.realestate.domain.attachment.dto.AttachmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Service
@RequiredArgsConstructor
public class AttachmentFacade {

    private final UploadServiceFactory uploadServiceFactory;
    private final AttachmentService attachmentService;

    public AttachmentDto upload(AttachmentCreateForm createForm) {
        UploadService uploadService = uploadServiceFactory.create(createForm.getFileType());
        AttachmentDto result = uploadService.upload(createForm);
        attachmentService.create(result);
        return result;
    }

}
