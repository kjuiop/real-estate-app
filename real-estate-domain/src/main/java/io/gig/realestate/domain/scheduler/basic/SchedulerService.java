package io.gig.realestate.domain.scheduler.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerSearchDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
public interface SchedulerService {

    Long create(SchedulerForm createForm, LoginUser loginUser);

    List<SchedulerListDto> getSchedulers(SchedulerSearchDto condition, LoginUser loginUser);

    SchedulerDetailDto getSchedulerById(Long schedulerId, LoginUser loginUser);

    Long update(SchedulerForm updateForm, LoginUser loginUser);

    Long deleteById(Long schedulerId, LoginUser loginUser);
}
