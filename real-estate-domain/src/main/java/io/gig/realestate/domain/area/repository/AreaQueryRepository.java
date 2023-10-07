package io.gig.realestate.domain.area.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.area.dto.AreaListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.area.QArea.area;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaQueryRepository {

    private final JPAQueryFactory queryFactory;


    public List<AreaListDto> getParentAreaList() {
        return this.queryFactory
                .select(Projections.constructor(AreaListDto.class, area))
                .from(area)
                .where(isNullParent())
                .where(eqLevel(1))
                .orderBy(area.sortOrder.asc())
                .fetch();
    }

    public List<AreaListDto> getAreaListByParentId(Long areaId) {
        return this.queryFactory
                .select(Projections.constructor(AreaListDto.class, area))
                .from(area)
                .where(eqParentId(areaId))
                .orderBy(area.sortOrder.asc())
                .fetch();
    }

    private BooleanExpression isNullParent() {
        return area.parent.isNull();
    }

    private BooleanExpression eqLevel(int level) {
        return area.level.eq(level);
    }

    private BooleanExpression eqParentId(Long areaId) {
        return areaId != null ? area.parent.id.eq(areaId) : null;
    }

}
