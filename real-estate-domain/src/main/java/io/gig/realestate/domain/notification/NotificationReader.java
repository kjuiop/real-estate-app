package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.notification.dto.NotificationListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/05/10
 */
public interface NotificationReader {
    Long getNotificationCntByUsername(String username);

    List<NotificationListDto> getNotificationByUsername(String username);

    Notification getNotificationById(Long id);
}
