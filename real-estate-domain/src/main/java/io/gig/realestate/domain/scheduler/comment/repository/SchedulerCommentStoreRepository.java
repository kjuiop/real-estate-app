package io.gig.realestate.domain.scheduler.comment.repository;

import io.gig.realestate.domain.scheduler.comment.SchedulerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Repository
public interface SchedulerCommentStoreRepository extends JpaRepository<SchedulerComment, Long> {
}
