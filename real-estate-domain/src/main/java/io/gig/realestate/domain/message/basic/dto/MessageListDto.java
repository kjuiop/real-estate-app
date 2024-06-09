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
public class MessageListDto {

    private Long messageId;

    private String message;

    private String returnUrl;

    private String sender;

    private String receiver;

    public static MessageListDto createMessage() {
        return MessageListDto.builder()
                .build();
    }

}
