package io.gig.realestate.domain.message.template.dto;

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

    public AlarmTemplateDto(AlarmTemplate at) {
        this.alarmTemplateId = at.getId();
    }
}
