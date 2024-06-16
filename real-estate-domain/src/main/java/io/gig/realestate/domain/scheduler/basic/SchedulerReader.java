package io.gig.realestate.domain.scheduler.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
public interface SchedulerReader {
    List<SchedulerListDto> getSchedulers(Administrator loginUser);

    SchedulerDetailDto getSchedulerById(Long schedulerId);

    Scheduler getSchedulerEntity(Long schedulerId);
}
