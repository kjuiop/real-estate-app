package io.gig.realestate.domain.attachment;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
public interface AttachmentStore {
    Attachment store(Attachment newAttachment);
    void storeAll(List<Attachment> attachmentList);
}
