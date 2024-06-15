package io.gig.realestate.domain.scheduler.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import io.gig.realestate.domain.scheduler.manager.SchedulerManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final AdministratorService administratorService;
    private final SchedulerManagerService schedulerManagerService;

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

    @Override
    @Transactional
    public Long update(SchedulerForm updateForm, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        Scheduler scheduler = schedulerReader.getSchedulerEntity(updateForm.getSchedulerId());
        scheduler.update(updateForm, loginAdmin);

        if (scheduler.getManagers() != null && !scheduler.getManagers().isEmpty()) {
            for (SchedulerManager sm : scheduler.getManagers()) {
                boolean existsInManager = updateForm.getManagerIds().stream().anyMatch(id -> id.equals(sm.getAdmin().getId()));
                if (!existsInManager) {
                    sm.delete();
                }
            }
        }

        if (updateForm.getManagerIds() != null && !updateForm.getManagerIds().contains(loginAdmin.getId())) {
            updateForm.getManagerIds().add(loginAdmin.getId());
        }

        for (Long adminId : updateForm.getManagerIds()) {
            Administrator manager = administratorService.getAdminById(adminId);
            Optional<SchedulerManager> findSchedulerManager = schedulerManagerService.getSchedulerManager(scheduler, manager);
            SchedulerManager schedulerManager;
            if (findSchedulerManager.isPresent()) {
                schedulerManager = findSchedulerManager.get();
                schedulerManager.update(scheduler, manager, loginAdmin);
            } else {
                schedulerManager = SchedulerManager.create(scheduler, manager, loginUser.getLoginUser());
                scheduler.getManagers().add(schedulerManager);
            }
        }

        Scheduler savedScheduler = schedulerStore.store(scheduler);
        return savedScheduler.getId();
    }
}
