package io.gig.realestate.domain.message.template.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.message.template.AlarmTemplate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@SuperBuilder
@Getter
public class AlarmTemplateDto {

    private Long alarmTemplateId;

    private String templateCd;

    private String emailTemplateCd;

    private String title;

    private String landingUrl;

    private String smsMessage;

    private YnType smsSendYn;

    private YnType emailSendYn;

    private YnType pushSendYn;

    public AlarmTemplateDto(AlarmTemplate at) {
        this.alarmTemplateId = at.getId();
        this.templateCd = at.getTemplateCd();
        this.emailTemplateCd = at.getEmailTemplateCd();
        this.title = at.getTitle();
        this.landingUrl = at.getLandingUrl();
        this.smsMessage = at.getSmsMessage();
        this.smsSendYn = at.getSmsSendYn();
        this.emailSendYn = at.getEmailSendYn();
        this.pushSendYn = at.getPushSendYn();
    }
}
