package io.gig.realestate.domain.scheduler.comment;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentDto;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentForm;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
public interface SchedulerCommentService {
    List<SchedulerCommentDto> create(Long schedulerId, SchedulerCommentForm createForm, LoginUser loginUser);

    List<SchedulerCommentDto> getSchedulerComments(Long schedulerId);
}
