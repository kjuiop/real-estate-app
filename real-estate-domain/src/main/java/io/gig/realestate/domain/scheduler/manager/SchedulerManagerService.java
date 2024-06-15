package io.gig.realestate.domain.scheduler.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.basic.Scheduler;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/06/15
 */
public interface SchedulerManagerService {
    Optional<SchedulerManager> getSchedulerManager(Scheduler scheduler, Administrator manager);
}
