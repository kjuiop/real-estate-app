package io.gig.realestate.domain.message.basic;

import io.gig.realestate.domain.message.basic.dto.MessageForm;
import io.gig.realestate.domain.message.slack.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageStore messageStore;

    private final SlackService slackService;

    @Value("${domain}")
    private String domain;

    @Override
    @Transactional
    public void sendNotificationMsg(MessageForm form) throws IOException {
        String userId = slackService.getSlackIdByEmail(form.getReceiver());
        slackService.sendMessageToChannelByApp(userId, form.getMessage() + " " + domain + form.getReturnUrl());
        MessageInfo messageInfo = MessageInfo.create(form);
        messageStore.store(messageInfo);
    }
}
