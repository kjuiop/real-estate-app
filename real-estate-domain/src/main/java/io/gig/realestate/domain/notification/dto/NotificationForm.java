package io.gig.realestate.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Getter
@Setter
@Builder
public class NotificationForm {

    private String message;

    private String returnUrl;
}
