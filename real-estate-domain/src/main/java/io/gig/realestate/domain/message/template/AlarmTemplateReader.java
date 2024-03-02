package io.gig.realestate.domain.message.template;

import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
public interface AlarmTemplateReader {
    AlarmTemplateDetailDto getAlarmTemplateDetail(Long alarmTemplateId);

    AlarmTemplate getAlarmTemplateById(Long alarmTemplateId);
}
