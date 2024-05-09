package io.gig.realestate.domain.notification;

/**
 * @author : JAKE
 * @date : 2024/05/10
 */
public interface NotificationReader {
    Long getNotificationCntByUsername(String username);
}
