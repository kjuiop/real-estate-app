package io.gig.realestate.domain.message.template.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.message.template.AlarmTemplate;
import io.gig.realestate.domain.message.template.AlarmTemplateReader;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmTemplateQueryImpl implements AlarmTemplateReader {

    private final AlarmTemplateQueryRepository queryRepository;

    @Override
    public AlarmTemplateDetailDto getAlarmTemplateDetail(Long alarmTemplateId) {

        Optional<AlarmTemplateDetailDto> findDetail = queryRepository.getAlarmTemplateDetail(alarmTemplateId);
        if (findDetail.isEmpty()) {
            throw new NotFoundException(alarmTemplateId + "의 정보가 없습니다.");
        }

        return findDetail.get();
    }

    @Override
    public AlarmTemplate getAlarmTemplateById(Long alarmTemplateId) {

        Optional<AlarmTemplate> findDetail = queryRepository.getAlarmTemplateById(alarmTemplateId);
        if (findDetail.isEmpty()) {
            throw new NotFoundException(alarmTemplateId + "의 정보가 없습니다.");
        }

        return findDetail.get();
    }
}
