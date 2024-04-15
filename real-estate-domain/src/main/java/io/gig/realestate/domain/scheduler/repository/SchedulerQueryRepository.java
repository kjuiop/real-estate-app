package io.gig.realestate.domain.scheduler.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.dto.SchedulerListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.scheduler.QScheduler.scheduler;

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

    private BooleanExpression defaultCondition() {
        return scheduler.deleteYn.eq(YnType.N);
    }

}
