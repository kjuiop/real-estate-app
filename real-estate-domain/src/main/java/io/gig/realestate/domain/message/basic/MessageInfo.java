package io.gig.realestate.domain.message.basic;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.message.basic.dto.MessageForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType readYn = YnType.N;

    private String message;

    private String returnUrl;

    private String sender;

    private String receiver;

    public static MessageInfo create(MessageForm form) {
        return MessageInfo.builder()
                .message(form.getMessage())
                .returnUrl(form.getReturnUrl())
                .sender(form.getSender())
                .receiver(form.getReceiver())
                .build();
    }
}
