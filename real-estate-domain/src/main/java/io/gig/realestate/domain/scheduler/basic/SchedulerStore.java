package io.gig.realestate.domain.scheduler.basic;

import io.gig.realestate.domain.scheduler.basic.Scheduler;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
public interface SchedulerStore {
    Scheduler store(Scheduler scheduler);
}
