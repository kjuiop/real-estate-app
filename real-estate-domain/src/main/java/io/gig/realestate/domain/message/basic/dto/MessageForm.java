package io.gig.realestate.domain.message.basic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Setter
@Getter
@Builder
public class MessageForm {

    private Long notificationId;

    private Long messageId;

    private String message;

    private String returnUrl;

    private String sender;

    private String receiver;

    public static MessageForm sendMsgByNotification(Long notiId, String message, String returnUrl, String sender, String receiver) {
        return MessageForm.builder()
                .notificationId(notiId)
                .message(message)
                .returnUrl(returnUrl)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
