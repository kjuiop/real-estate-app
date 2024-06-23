package io.gig.realestate.domain.scheduler.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.scheduler.comment.QSchedulerComment.schedulerComment;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulerCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SchedulerCommentDto> getSchedulerComments(Long schedulerId) {
        return this.queryFactory
                .select(Projections.constructor(SchedulerCommentDto.class,
                        schedulerComment))
                .from(schedulerComment)
                .where(defaultCondition())
                .where(eqSchedulerId(schedulerId))
                .orderBy(schedulerComment.createdAt.desc())
                .fetch()
                ;
    }

    private BooleanExpression defaultCondition() {
        return schedulerComment.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqSchedulerId(Long schedulerId) {
        return schedulerId != null ? schedulerComment.scheduler.id.eq(schedulerId) : null;
    }

}
