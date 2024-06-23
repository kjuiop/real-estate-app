package io.gig.realestate.domain.scheduler.comment;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.SchedulerReader;
import io.gig.realestate.domain.scheduler.basic.SchedulerService;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentDto;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerCommentServiceImpl implements SchedulerCommentService {

    private final SchedulerReader schedulerReader;

    private final SchedulerCommentReader commentReader;

    private final SchedulerCommentStore commentStore;

    @Override
    @Transactional
    public List<SchedulerCommentDto> create(Long schedulerId, SchedulerCommentForm createForm, LoginUser loginUser) {
        Scheduler scheduler = schedulerReader.getSchedulerEntity(schedulerId);
        Administrator loginAdmin = loginUser.getLoginUser();
        SchedulerComment comment = SchedulerComment.create(createForm, scheduler, loginAdmin);
        commentStore.store(comment);
        return commentReader.getSchedulerComments(schedulerId);
    }

    @Override
    @Transactional
    public List<SchedulerCommentDto> getSchedulerComments(Long schedulerId) {
        return commentReader.getSchedulerComments(schedulerId);
    }
}
