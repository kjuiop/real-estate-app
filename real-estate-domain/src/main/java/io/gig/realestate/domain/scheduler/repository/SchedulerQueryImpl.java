package io.gig.realestate.domain.scheduler.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.SchedulerReader;
import io.gig.realestate.domain.scheduler.dto.SchedulerDto;
import io.gig.realestate.domain.scheduler.dto.SchedulerListDto;
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
    public List<SchedulerListDto> getSchedulers(Administrator loginUser) {
        return queryRepository.getSchedulers(loginUser);
    }

    @Override
    public SchedulerDto getSchedulerById(Long schedulerId) {
        return queryRepository.getSchedulerById(schedulerId);
    }
}
