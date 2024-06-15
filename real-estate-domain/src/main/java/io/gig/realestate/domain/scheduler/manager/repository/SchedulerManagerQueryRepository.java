package io.gig.realestate.domain.scheduler.manager.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.scheduler.manager.QSchedulerManager.schedulerManager;


/**
 * @author : JAKE
 * @date : 2024/06/15
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulerManagerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<SchedulerManager> getSchedulerManager(Scheduler scheduler, Administrator manager) {
        Optional<SchedulerManager> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(schedulerManager)
                .where(defaultCondition())
                .where(schedulerManager.scheduler.eq(scheduler))
                .where(schedulerManager.admin.eq(manager))
                .limit(1)
                .fetchFirst()
        );

        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return schedulerManager.deleteYn.eq(YnType.N);
    }
}
