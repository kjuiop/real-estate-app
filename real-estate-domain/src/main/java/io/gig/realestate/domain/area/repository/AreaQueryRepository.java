package io.gig.realestate.domain.area.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.area.Area;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.area.QArea.area;
import static io.gig.realestate.domain.realestate.basic.QRealEstate.realEstate;

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
                .where(defaultCondition())
                .where(isNullParent())
                .where(eqLevel(1))
                .orderBy(area.name.asc())
                .fetch();
    }

    public List<AreaListDto> getAreaListByParentId(Long areaId) {
        return this.queryFactory
                .select(Projections.constructor(AreaListDto.class, area))
                .from(area)
                .where(defaultCondition())
                .where(eqParentId(areaId))
                .orderBy(area.name.asc())
                .fetch();
    }

    public List<AreaListDto> getAreaListBySido(String sido) {
        return this.queryFactory
                .select(Projections.constructor(AreaListDto.class, area))
                .from(area)
                .where(defaultCondition())
                .where(eqSido(sido))
                .where(eqLevel(2))
                .orderBy(area.name.asc())
                .fetch();
    }

    public List<AreaListDto> getAreaListByGungu(String gungu) {
        return this.queryFactory
                .select(Projections.constructor(AreaListDto.class, area))
                .from(area)
                .where(defaultCondition())
                .where(eqGungu(gungu))
                .where(eqLevel(3))
                .orderBy(area.name.asc())
                .fetch();
    }

    public Optional<Area> getAreaLikeNameAndArea(String name, String sido, String gungu, String dong) {
        Optional<Area> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(area)
                .where(defaultCondition())
                .where(likeName(name))
                .where(likeSidoName(sido))
                .where(likeGunguName(gungu))
                .where(likeDongName(dong))
                .where(eqLevel(3))
                .limit(1)
                .fetchFirst()
        );

        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return area.deleteYn.eq(YnType.N);
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

    private BooleanExpression eqSido(String sido) {
        if (!StringUtils.hasText(sido) || sido.length() < 3) {
            return null;
        }

        String legalCode = sido.substring(0, 2);
        return area.legalAddressCode.startsWith(legalCode);
    }

    private BooleanExpression eqGungu(String gungu) {
        if (!StringUtils.hasText(gungu) || gungu.length() < 6) {
            return null;
        }

        String legalCode = gungu.substring(0, 5);
        return area.legalAddressCode.startsWith(legalCode);
    }

    private BooleanExpression likeSidoName(String sido) {
        if (!StringUtils.hasText(sido)) {
            return null;
        }

        return area.sido.like("%" + sido + "%");
    }

    private BooleanExpression likeGunguName(String gungu) {
        if (!StringUtils.hasText(gungu)) {
            return null;
        }

        return area.gungu.like("%" + gungu + "%");
    }

    private BooleanExpression likeDongName(String dong) {
        if (!StringUtils.hasText(dong)) {
            return null;
        }

        return area.dong.like("%" + dong + "%");
    }

    private BooleanExpression likeName(String name) {
        return StringUtils.hasText(name) ? area.name.like("%" + name + "%") : null;
    }
}
