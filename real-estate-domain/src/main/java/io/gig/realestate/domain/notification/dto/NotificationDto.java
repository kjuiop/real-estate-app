package io.gig.realestate.domain.notification.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.notification.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

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

    private String afterPass;

    public NotificationDto(Notification n) {
        this.notificationId = n.getId();
        this.message = n.getMessage();
        this.readYn = n.getReadYn();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(n.getCreatedAt(), now);
        long pass = duration.toDays();
        if (pass == 0) {
            this.afterPass = "today";
        } else {
            this.afterPass = duration.toDays() + " days";
        }
        this.returnUrl = n.getReturnUrl();
    }
}
