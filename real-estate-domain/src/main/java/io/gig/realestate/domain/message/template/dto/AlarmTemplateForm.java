package io.gig.realestate.domain.message.template.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Getter
@Setter
public class AlarmTemplateForm {

    private Long alarmTemplateId;

    private String templateCd;

    private String emailTemplateCd;

    private String title;

    private String landingUrl;

    private String smsMessage;

    private YnType smsSendYn;

    private YnType emailSendYn;

    private YnType pushSendYn;

}
