package io.gig.realestate.domain.message.template;

import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateListDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
public interface AlarmTemplateReader {
    AlarmTemplateDetailDto getAlarmTemplateDetail(Long alarmTemplateId);

    AlarmTemplate getAlarmTemplateById(Long alarmTemplateId);

    Page<AlarmTemplateListDto> getAlarmTemplatePageListBySearch(AlarmTemplateSearchDto condition);
}
