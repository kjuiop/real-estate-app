package io.gig.realestate.domain.message.template;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateForm;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateListDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
public interface AlarmTemplateService {

    AlarmTemplateDetailDto getAlarmTemplateDetail(Long alarmTemplateId);

    Long create(AlarmTemplateForm createForm, LoginUser loginUser);

    Long update(AlarmTemplateForm updateForm, LoginUser loginUser);

    Page<AlarmTemplateListDto> getAlarmTemplatePageListBySearch(AlarmTemplateSearchDto condition);
}
