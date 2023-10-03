package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.dto.AttachmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentStore attachmentStore;

    @Override
    @Transactional
    public void create(AttachmentDto createForm) {
        Attachment newAttachment = Attachment.Of(createForm.getUsageType(), createForm.getFileType(),
                createForm.getOriginalFilename(), createForm.getSavedFilename(), createForm.getFullPath());

        attachmentStore.store(newAttachment);
    }
}
