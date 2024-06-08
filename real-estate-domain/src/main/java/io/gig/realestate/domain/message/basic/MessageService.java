package io.gig.realestate.domain.message.basic;

import io.gig.realestate.domain.message.basic.dto.MessageForm;

import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
public interface MessageService {
    void sendNotificationMsg(MessageForm form) throws IOException;
}
