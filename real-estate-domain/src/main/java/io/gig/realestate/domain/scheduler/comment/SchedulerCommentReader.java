package io.gig.realestate.domain.scheduler.comment;

import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
public interface SchedulerCommentReader {
    List<SchedulerCommentDto> getSchedulerComments(Long schedulerId);
}
