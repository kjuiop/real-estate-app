package io.gig.realestate.domain.buyer.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author : JAKE
 * @date : 2024/06/09
 */
@Getter
public class BuyerOverDueEvent extends ApplicationEvent {

    private String queueName;

    public BuyerOverDueEvent(Object source) {
        super(source);
    }

    public BuyerOverDueEvent(Object source, String queueName) {
        super(source);
        this.queueName = queueName;
    }
}
