package io.gig.realestate.domain.notification.repository;

import io.gig.realestate.domain.notification.Notification;
import io.gig.realestate.domain.notification.NotificationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Component
@Transactional
@RequiredArgsConstructor
public class NotificationStoreImpl implements NotificationStore {

    private final NotificationStoreRepository storeRepository;

    @Override
    public Notification store(Notification notification) {
        return storeRepository.save(notification);
    }
}
