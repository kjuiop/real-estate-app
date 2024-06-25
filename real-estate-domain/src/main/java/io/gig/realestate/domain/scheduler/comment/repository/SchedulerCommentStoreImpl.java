package io.gig.realestate.domain.scheduler.comment.repository;

import io.gig.realestate.domain.scheduler.comment.SchedulerComment;
import io.gig.realestate.domain.scheduler.comment.SchedulerCommentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Component
@Transactional
@RequiredArgsConstructor
public class SchedulerCommentStoreImpl implements SchedulerCommentStore {

    private final SchedulerCommentStoreRepository queryRepository;

    @Override
    public SchedulerComment store(SchedulerComment comment) {
        return queryRepository.save(comment);
    }
}
