package io.gig.realestate.domain.message.basic.repository;

import io.gig.realestate.domain.message.basic.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Repository
public interface MessageStoreRepository extends JpaRepository<MessageInfo, Long> {
}
