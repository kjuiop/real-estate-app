package io.gig.realestate.domain.scheduler.comment;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentForm;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
public interface SchedulerCommentService {
    Long create(Long schedulerId, SchedulerCommentForm createForm, LoginUser loginUser);
}
