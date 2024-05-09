package io.gig.realestate.domain.notification.repository;

import io.gig.realestate.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Repository
public interface NotificationStoreRepository extends JpaRepository<Notification, Long> {
}
