package io.gig.realestate.domain.scheduler.comment.repository;

import io.gig.realestate.domain.scheduler.comment.SchedulerCommentReader;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerCommentQueryImpl implements SchedulerCommentReader {

    private final SchedulerCommentQueryRepository queryRepository;

    @Override
    public List<SchedulerCommentDto> getSchedulerComments(Long schedulerId) {
        return queryRepository.getSchedulerComments(schedulerId);
    }
}
