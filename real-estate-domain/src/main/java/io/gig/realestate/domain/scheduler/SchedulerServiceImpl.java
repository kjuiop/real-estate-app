package io.gig.realestate.domain.scheduler;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.scheduler.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.dto.SchedulerDto;
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

    private final AdministratorService administratorService;

    private final SchedulerReader schedulerReader;
    private final SchedulerStore schedulerStore;

    @Override
    @Transactional(readOnly = true)
    public List<SchedulerListDto> getSchedulers(LoginUser loginUser) {
        return schedulerReader.getSchedulers(loginUser.getLoginUser());
    }

    @Override
    @Transactional(readOnly = true)
    public SchedulerDetailDto getSchedulerById(Long schedulerId, LoginUser loginUser) {
        return schedulerReader.getSchedulerById(schedulerId);
    }

    @Override
    @Transactional
    public Long create(SchedulerForm createForm, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        Scheduler scheduler = Scheduler.create(createForm, loginUser.getLoginUser());

        if (createForm.getManagerIds() != null && !createForm.getManagerIds().contains(loginAdmin.getId())) {
            createForm.getManagerIds().add(loginAdmin.getId());
        }

        for (Long adminId : createForm.getManagerIds()) {
            Administrator manager = administratorService.getAdminById(adminId);
            SchedulerManager schedulerManager = SchedulerManager.create(scheduler, manager, loginAdmin);
            scheduler.addManager(schedulerManager);
        }

        Scheduler savedScheduler = schedulerStore.store(scheduler);
        return savedScheduler.getId();
    }
}
