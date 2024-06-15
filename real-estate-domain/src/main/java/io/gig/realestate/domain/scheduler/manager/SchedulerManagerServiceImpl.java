package io.gig.realestate.domain.scheduler.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/06/15
 */
@Service
@RequiredArgsConstructor
public class SchedulerManagerServiceImpl implements SchedulerManagerService {

    private final SchedulerManagerReader schedulerManagerReader;

    @Override
    @Transactional(readOnly = true)
    public Optional<SchedulerManager> getSchedulerManager(Scheduler scheduler, Administrator manager) {
        return schedulerManagerReader.getSchedulerManager(scheduler, manager);
    }
}
