package io.gig.realestate.domain.realestate.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Getter
public class RealEstateEvent extends ApplicationEvent {

    private String queueName;

    public RealEstateEvent(Object source) {
        super(source);
    }

    public RealEstateEvent(Object source, String queueName) {
        super(source);
        this.queueName = queueName;
    }
}
