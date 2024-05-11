package io.gig.realestate.domain.notification.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.notification.Notification;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/05/11
 */
@Getter
@Setter
public class NotificationDto {

    private Long notificationId;

    private String message;

    private YnType readYn;

    private String returnUrl;

    public NotificationDto(Notification n) {
        this.notificationId = n.getId();
        this.message = n.getMessage();
        this.readYn = n.getReadYn();
        this.returnUrl = n.getReturnUrl();
    }
}
