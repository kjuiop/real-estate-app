package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType readYn = YnType.N;

    private String returnUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Administrator sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Administrator receiver;

    public static Notification create(NotificationForm createForm, Administrator loginUser) {
        return Notification.builder()
                .message(createForm.getMessage())
                .returnUrl(createForm.getReturnUrl())
                .receiver(loginUser)
                .build();
    }

    public static Notification sendBuyerCreateManager(String message, String returnUrl, Administrator sender, Administrator receiver) {
        return Notification.builder()
                .message(message)
                .returnUrl(returnUrl)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
