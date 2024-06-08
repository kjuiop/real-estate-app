package io.gig.realestate.domain.notification.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Getter
public class NotificationEvent extends ApplicationEvent {

    private String queueName;

    public NotificationEvent(Object source) {
        super(source);
    }

    public NotificationEvent(Object source, String queueName) {
        super(source);
        this.queueName = queueName;
    }
}
