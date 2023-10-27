package io.gig.realestate.domain.attachment.repository;

import io.gig.realestate.domain.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
@Repository
public interface AttachmentStoreRepository extends JpaRepository<Attachment, Long> {
}
