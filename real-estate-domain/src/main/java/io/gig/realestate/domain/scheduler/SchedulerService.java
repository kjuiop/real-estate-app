package io.gig.realestate.domain.scheduler;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.dto.SchedulerListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
public interface SchedulerService {

    Long create(SchedulerForm createForm, LoginUser loginUser);

    List<SchedulerListDto> getSchedulers(LoginUser loginUser);
}
