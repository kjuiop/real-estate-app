package io.gig.realestate.domain.attachment.repository;

import io.gig.realestate.domain.attachment.Attachment;
import io.gig.realestate.domain.attachment.AttachmentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
@Component
@Transactional
@RequiredArgsConstructor
public class AttachmentStoreImpl implements AttachmentStore {

    private final AttachmentStoreRepository attachmentStoreRepository;

    @Override
    public Attachment store(Attachment attachment) {
        return attachmentStoreRepository.save(attachment);
    }

    @Override
    public void storeAll(List<Attachment> attachmentList) {
        attachmentStoreRepository.saveAll(attachmentList);
    }
}
