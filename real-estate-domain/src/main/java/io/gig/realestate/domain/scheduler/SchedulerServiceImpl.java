package io.gig.realestate.domain.scheduler;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.dto.SchedulerListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final SchedulerReader schedulerReader;
    private final SchedulerStore schedulerStore;

    @Override
    @Transactional(readOnly = true)
    public List<SchedulerListDto> getSchedulers(LoginUser loginUser) {
        return schedulerReader.getSchedulers(loginUser.getLoginUser());
    }

    @Override
    @Transactional
    public Long create(SchedulerForm createForm, LoginUser loginUser) {
        Scheduler scheduler = Scheduler.create(createForm, loginUser.getLoginUser());
        return schedulerStore.store(scheduler).getId();
    }
}
