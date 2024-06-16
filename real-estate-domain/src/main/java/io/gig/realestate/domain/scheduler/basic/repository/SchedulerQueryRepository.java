package io.gig.realestate.domain.scheduler.basic.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.scheduler.basic.QScheduler.scheduler;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SchedulerListDto> getSchedulers(Administrator loginUser) {
        return this.queryFactory
                .select(Projections.constructor(SchedulerListDto.class,
                        scheduler))
                .from(scheduler)
                .where(defaultCondition())
                .orderBy(scheduler.createdAt.desc())
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

    private BooleanExpression eqScheduleId(Long schedulerId) {
        return schedulerId != null ? scheduler.id.eq(schedulerId) : null;
    }

    private BooleanExpression defaultCondition() {
        return scheduler.deleteYn.eq(YnType.N);
    }

}
