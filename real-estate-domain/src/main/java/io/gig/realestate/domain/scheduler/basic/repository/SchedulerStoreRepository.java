package io.gig.realestate.domain.scheduler.basic.repository;

import io.gig.realestate.domain.scheduler.basic.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Repository
public interface SchedulerStoreRepository extends JpaRepository<Scheduler, Long> {
}
