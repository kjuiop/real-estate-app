package io.gig.realestate.domain.scheduler.manager.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import io.gig.realestate.domain.scheduler.manager.SchedulerManagerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/06/15
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerManagerQueryImpl implements SchedulerManagerReader {

    private final SchedulerManagerQueryRepository queryRepository;

    @Override
    public Optional<SchedulerManager> getSchedulerManager(Scheduler scheduler, Administrator manager) {
        return queryRepository.getSchedulerManager(scheduler, manager);
    }
}
