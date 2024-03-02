package io.gig.realestate.domain.message.template;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmTemplateServiceImpl implements AlarmTemplateService {

    private final AlarmTemplateReader alarmTemplateReader;
    private final AlarmTemplateStore alarmTemplateStore;

    @Override
    @Transactional(readOnly = true)
    public AlarmTemplateDetailDto getAlarmTemplateDetail(Long alarmTemplateId) {
        return alarmTemplateReader.getAlarmTemplateDetail(alarmTemplateId);
    }

    @Override
    @Transactional
    public Long create(AlarmTemplateForm createForm, LoginUser loginUser) {
        AlarmTemplate alarmTemplate = AlarmTemplate.create(createForm, loginUser.getLoginUser());
        return alarmTemplateStore.store(alarmTemplate).getId();
    }

    @Override
    @Transactional
    public Long update(AlarmTemplateForm updateForm, LoginUser loginUser) {
        AlarmTemplate alarmTemplate = alarmTemplateReader.getAlarmTemplateById(updateForm.getAlarmTemplateId());
        alarmTemplate.update(updateForm, loginUser.getLoginUser());
        return alarmTemplate.getId();
    }
}
