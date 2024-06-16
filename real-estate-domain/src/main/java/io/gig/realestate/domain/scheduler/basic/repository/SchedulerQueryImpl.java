package io.gig.realestate.domain.scheduler.basic.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.SchedulerReader;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerQueryImpl implements SchedulerReader {

    private final SchedulerQueryRepository queryRepository;

    @Override
    public List<SchedulerListDto> getSchedulers(SchedulerSearchDto condition, Administrator loginUser) {
        return queryRepository.getSchedulers(condition, loginUser);
    }

    @Override
    public SchedulerDetailDto getSchedulerById(Long schedulerId) {
        return queryRepository.getSchedulerById(schedulerId);
    }

    @Override
    public Scheduler getSchedulerEntity(Long schedulerId) {
        return queryRepository.getSchedulerEntity(schedulerId);
    }
}
