package io.gig.realestate.domain.scheduler.repository;

import io.gig.realestate.domain.scheduler.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Repository
public interface SchedulerStoreRepository extends JpaRepository<Scheduler, Long> {
}
