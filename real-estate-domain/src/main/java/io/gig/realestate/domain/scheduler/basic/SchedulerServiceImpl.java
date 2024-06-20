package io.gig.realestate.domain.scheduler.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.notification.NotificationService;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerSearchDto;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import io.gig.realestate.domain.scheduler.manager.SchedulerManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private final NotificationService notificationService;
    private final CategoryService categoryService;

    private final SchedulerReader schedulerReader;
    private final SchedulerStore schedulerStore;

    @Override
    @Transactional(readOnly = true)
    public List<SchedulerListDto> getSchedulers(SchedulerSearchDto condition, LoginUser loginUser) {
        return schedulerReader.getSchedulers(condition, loginUser.getLoginUser());
    }

    @Override
    @Transactional(readOnly = true)
    public SchedulerDetailDto getSchedulerById(Long schedulerId, LoginUser loginUser) {
        return schedulerReader.getSchedulerById(schedulerId);
    }

    @Override
    @Transactional
    public Long deleteById(Long schedulerId, LoginUser loginUser) {
        Scheduler scheduler = schedulerReader.getSchedulerEntity(schedulerId);
        scheduler.delete(loginUser.getLoginUser());
        return scheduler.getId();
    }

    @Override
    @Transactional
    public Long create(SchedulerForm createForm, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        String colorCode = "";
        if (StringUtils.hasText(createForm.getPriorityOrderCds())) {
            Category category = categoryService.getCategoryByCode(createForm.getPriorityOrderCds());
            colorCode = category.getColorCode();
        }
        Scheduler scheduler = Scheduler.create(createForm, colorCode, loginUser.getLoginUser());

        if (createForm.getManagerIds() != null && !createForm.getManagerIds().contains(loginAdmin.getId())) {
            createForm.getManagerIds().add(loginAdmin.getId());
        }

        for (Long adminId : createForm.getManagerIds()) {
            Administrator manager = administratorService.getAdminById(adminId);
            SchedulerManager schedulerManager = SchedulerManager.create(scheduler, manager, loginAdmin);
            scheduler.addManager(schedulerManager);
        }

        Scheduler savedScheduler = schedulerStore.store(scheduler);
        notificationService.sendSchedulerCreateToManager(savedScheduler.getId(), savedScheduler.getCustomerName(), loginAdmin.getId(), createForm.getManagerIds());
        return savedScheduler.getId();
    }

    @Override
    @Transactional
    public Long update(SchedulerForm updateForm, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        String colorCode = "";
        if (StringUtils.hasText(updateForm.getPriorityOrderCds())) {
            Category category = categoryService.getCategoryByCode(updateForm.getPriorityOrderCds());
            colorCode = category.getColorCode();
        }
        Scheduler scheduler = schedulerReader.getSchedulerEntity(updateForm.getSchedulerId());
        scheduler.update(updateForm, colorCode, loginAdmin);

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
        notificationService.sendSchedulerUpdateToManager(savedScheduler.getId(), savedScheduler.getCustomerName(), loginAdmin.getId(), updateForm.getManagerIds());
        return savedScheduler.getId();
    }
}
