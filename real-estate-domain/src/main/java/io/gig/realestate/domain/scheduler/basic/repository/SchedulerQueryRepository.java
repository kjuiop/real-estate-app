package io.gig.realestate.domain.scheduler.basic.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorRole;
import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.gig.realestate.domain.scheduler.basic.QScheduler.scheduler;
import static io.gig.realestate.domain.scheduler.manager.QSchedulerManager.schedulerManager;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SchedulerListDto> getSchedulers(SchedulerSearchDto condition, Administrator loginUser) {
        return this.queryFactory
                .select(Projections.constructor(SchedulerListDto.class,
                        scheduler))
                .from(scheduler)
                .where(defaultCondition())
                .where(adminStatusNotWithdraw())
                .where(inSchedulerManager(condition.getAdminId()))
                .where(ownManager(loginUser))
                .where(eqBuyerId(condition.getBuyerId()))
                .where(eqPriorityOrderCds(condition.getPriorityOrderCds()))
                .where(eqProcessCds(condition.getProcessCds()))
                .fetch()
                ;
    }

    public SchedulerDetailDto getSchedulerById(Long schedulerId) {
        return this.queryFactory
                .select(Projections.constructor(SchedulerDetailDto.class,
                        scheduler
                        ))
                .from(scheduler)
                .where(defaultCondition())
                .where(eqScheduleId(schedulerId))
                .fetchOne();
    }

    public Scheduler getSchedulerEntity(Long schedulerId) {
        return queryFactory
                .selectFrom(scheduler)
                .where(defaultCondition())
                .where(eqScheduleId(schedulerId))
                .limit(1)
                .fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return scheduler.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqScheduleId(Long schedulerId) {
        return schedulerId != null ? scheduler.id.eq(schedulerId) : null;
    }

    private BooleanExpression eqPriorityOrderCds(String priorityOrderCds) {
        return StringUtils.hasText(priorityOrderCds) ? scheduler.priorityOrderCds.eq(priorityOrderCds) : null;
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return buyerId != null ? scheduler.buyer.id.eq(buyerId) : null;
    }

    private BooleanExpression eqProcessCds(String processCds) {
        return StringUtils.hasText(processCds) ? scheduler.processCds.eq(processCds) : null;
    }

    private BooleanExpression inSchedulerManager(Long adminId) {
        if (adminId == null) {
            return null;
        }

        return scheduler.id.in(
                JPAExpressions.selectDistinct(schedulerManager.scheduler.id)
                        .from(schedulerManager)
                        .where(schedulerManager.deleteYn.eq(YnType.N))
                        .where(schedulerManager.admin.id.in(adminId))
        );
    }

    private BooleanExpression ownManager(Administrator loginUser) {
        for (AdministratorRole adminRole : loginUser.getAdministratorRoles()) {
            if (adminRole.getRole().getName().equals("ROLE_SUPER_ADMIN")) {
                return null;
            }
        }

        return scheduler.id.in(
                JPAExpressions.selectDistinct(schedulerManager.scheduler.id)
                        .from(schedulerManager)
                        .where(schedulerManager.deleteYn.eq(YnType.N))
                        .where(schedulerManager.admin.id.eq(loginUser.getId())));
    }

    private BooleanExpression adminStatusNotWithdraw() {
        return scheduler.createdBy.status.ne(AdminStatus.WITHDRAW)
                .and(scheduler.createdBy.deleteYn.eq(YnType.N))
                ;
    }
}
