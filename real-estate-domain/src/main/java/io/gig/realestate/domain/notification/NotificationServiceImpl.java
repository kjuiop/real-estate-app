package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationReader notificationReader;
    private final NotificationStore notificationStore;

    @Override
    @Transactional(readOnly = true)
    public Long getNotificationCntByUsername(String username) {
        return notificationReader.getNotificationCntByUsername(username);
    }

    @Override
    @Transactional
    public Long create(NotificationForm form, Administrator administrator) {
        Notification notification = Notification.create(form, administrator);
        return notificationStore.store(notification).getId();
    }
}
