package io.gig.realestate.domain.message.template;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateForm;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
public interface AlarmTemplateService {
    Long create(AlarmTemplateForm createForm, LoginUser loginUser);

    AlarmTemplateDetailDto getAlarmTemplateDetail(Long alarmTemplateId);
}
