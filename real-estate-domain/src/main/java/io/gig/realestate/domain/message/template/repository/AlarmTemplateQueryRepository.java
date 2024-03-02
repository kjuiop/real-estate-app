package io.gig.realestate.domain.message.template.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.message.template.AlarmTemplate;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static io.gig.realestate.domain.message.template.QAlarmTemplate.alarmTemplate;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmTemplateQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<AlarmTemplateDetailDto> getAlarmTemplateDetail(Long alarmTemplateId) {

        AlarmTemplateDetailDto fetch = queryFactory
                .select(Projections.constructor(AlarmTemplateDetailDto.class,
                        alarmTemplate
                        ))
                .from(alarmTemplate)
                .where(defaultCondition())
                .where(eqAlarmTemplateId(alarmTemplateId))
                .limit(1)
                .fetchOne()
                ;

        return Optional.ofNullable(fetch);
    }

    public Optional<AlarmTemplate> getAlarmTemplateById(Long alarmTemplateId) {

        AlarmTemplate fetch = queryFactory
                .selectFrom(alarmTemplate)
                .where(defaultCondition())
                .where(eqAlarmTemplateId(alarmTemplateId))
                .limit(1)
                .fetchOne()
                ;

        return Optional.ofNullable(fetch);
    }

    private BooleanExpression defaultCondition() {
        return alarmTemplate.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqAlarmTemplateId(Long alarmTemplateId) {
        return alarmTemplateId != null ? alarmTemplate.id.eq(alarmTemplateId) : null;
    }
}
