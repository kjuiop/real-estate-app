package io.gig.realestate.domain.message.event;

import io.gig.realestate.domain.message.basic.MessageService;
import io.gig.realestate.domain.message.basic.dto.MessageForm;
import io.gig.realestate.domain.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener implements ApplicationListener<NotificationEvent> {

    private final MessageService messageService;

    @SneakyThrows
    @Async
    @Override
    public void onApplicationEvent(NotificationEvent event) {
        log.info("event : " + event.getQueueName());
        MessageForm form = (MessageForm) event.getSource();
        messageService.sendNotificationMsg(form);
    }
}
