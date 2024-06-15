package io.gig.realestate.domain.scheduler.basic.repository;

import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.SchedulerStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Component
@Transactional
@RequiredArgsConstructor
public class SchedulerStoreImpl implements SchedulerStore {

    private final SchedulerStoreRepository storeRepository;

    @Override
    public Scheduler store(Scheduler scheduler) {
        return storeRepository.save(scheduler);
    }
}
