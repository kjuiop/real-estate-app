package io.gig.realestate.domain.scheduler.comment;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.SchedulerService;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerCommentServiceImpl implements SchedulerCommentService {

    private final SchedulerService schedulerService;
    private final SchedulerCommentStore commentStore;

    @Override
    @Transactional
    public Long create(Long schedulerId, SchedulerCommentForm createForm, LoginUser loginUser) {
        Scheduler scheduler = schedulerService.getSchedulerEntityById(schedulerId);
        Administrator loginAdmin = loginUser.getLoginUser();
        SchedulerComment comment = SchedulerComment.create(createForm, scheduler, loginAdmin);
        SchedulerComment savedComment = commentStore.store(comment);
        return savedComment.getId();
    }
}
