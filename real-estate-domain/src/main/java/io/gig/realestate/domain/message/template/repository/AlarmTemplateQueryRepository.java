package io.gig.realestate.domain.message.template.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.message.template.AlarmTemplate;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateListDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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

    public Page<AlarmTemplateListDto> getAlarmTemplatePageListBySearch(AlarmTemplateSearchDto condition) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(likeTitle(condition.getTitle()));

        JPAQuery<AlarmTemplateListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AlarmTemplateListDto.class,
                        alarmTemplate
                        ))
                .from(alarmTemplate)
                .where(where)
                .orderBy(alarmTemplate.id.desc())
                .limit(condition.getPageableWithSort().getPageSize())
                .offset(condition.getPageableWithSort().getOffset());

        List<AlarmTemplateListDto> content = contentQuery.fetch();

        Long total = queryFactory
                .select(alarmTemplate.count())
                .from(alarmTemplate)
                .where(where)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, condition.getPageableWithSort(), total);
    }

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

    private BooleanExpression likeTitle(String title) {
        return StringUtils.hasText(title) ? alarmTemplate.title.like("%" + title + "%") : null;
    }
}
