package io.gig.realestate.domain.scheduler;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.dto.SchedulerListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
public interface SchedulerReader {
    List<SchedulerListDto> getSchedulers(Administrator loginUser);
}
