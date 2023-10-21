package io.gig.realestate.domain.realestate.basic.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.land.QLandInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.realestate.basic.QRealEstate.realEstate;
import static io.gig.realestate.domain.realestate.land.QLandInfo.landInfo;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealEstateQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(eqProcessType(searchDto.getProcessType()));
        where.and(eqSido(searchDto.getSido()));
        where.and(eqGungu(searchDto.getGungu()));
        where.and(eqDong(searchDto.getDong()));
        where.and(eqLandType(searchDto.getLandType()));
        where.and(eqBun(searchDto.getBun()));
        where.and(eqJi(searchDto.getJi()));
        where.and(likeBuildingName(searchDto.getBuildingName()));
        where.and(eqRealEstateId(searchDto.getRealEstateId()));
        where.and(likePrposArea1Nm(searchDto.getPrposArea1Nm()));
        where.and(likeManagerName(searchDto.getManager()));
        where.and(likeTeamName(searchDto.getTeam()));

        JPAQuery<RealEstateListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(RealEstateListDto.class,
                        realEstate
                ))
                .from(realEstate)
                .where(where)
                .orderBy(realEstate.id.desc())
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        List<RealEstateListDto> content = contentQuery.fetch();

        Long total = queryFactory
                .select(realEstate.count())
                .from(realEstate)
                .where(where)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }

    public Optional<RealEstateDetailDto> getRealEstateDetail(Long realEstateId) {

        RealEstateDetailDto realEstateDetailDto = queryFactory
                .select(Projections.constructor(RealEstateDetailDto.class,
                        realEstate
                ))
                .from(realEstate)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(realEstateDetailDto);
    }



    public Optional<RealEstate> getRealEstateById(Long realEstateId) {
        Optional<RealEstate> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(realEstate)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return realEstate.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? realEstate.id.eq(realEstateId) : null;
    }

    private BooleanExpression eqProcessType(ProcessType processType) {
        return processType != null ? realEstate.processType.eq(processType) : null;
    }

    private BooleanExpression eqSido(String sido) {
        if (!StringUtils.hasText(sido) || sido.length() < 3) {
            return null;
        }

        String legalCode = sido.substring(0, 2);
        return realEstate.legalCode.startsWith(legalCode);
    }

    private BooleanExpression eqGungu(String gungu) {
        if (!StringUtils.hasText(gungu) || gungu.length() < 5) {
            return null;
        }
        String legalCode = gungu.substring(0, 5);
        return realEstate.legalCode.startsWith(legalCode);
    }

    private BooleanExpression eqDong(String dong) {
        if (!StringUtils.hasText(dong) || dong.length() < 8) {
            return null;
        }
        String legalCode = dong.substring(0, 8);
        return realEstate.legalCode.startsWith(legalCode);
    }

    private BooleanExpression eqLandType(String landType) {
        return StringUtils.hasText(landType) ? realEstate.landType.eq(landType) : null;
    }

    private BooleanExpression eqBun(String bun) {
        return StringUtils.hasText(bun) ? realEstate.bun.eq(bun) : null;
    }

    private BooleanExpression eqJi(String ji) {
        return StringUtils.hasText(ji) ? realEstate.ji.eq(ji) : null;
    }

    private BooleanExpression likeBuildingName(String buildingName) {
        return StringUtils.hasText(buildingName) ? realEstate.buildingName.like("%" + buildingName + "%") : null;
    }

    private BooleanExpression likePrposArea1Nm(String prposArea1Nm) {
        if (!StringUtils.hasText(prposArea1Nm)) {
            return null;
        }
        return realEstate.id.in(
                JPAExpressions.selectDistinct(landInfo.realEstate.id)
                        .from(landInfo)
                        .where(landInfo.prposArea1Nm.like("%" + prposArea1Nm + "%"))
        );
    }

    private BooleanExpression likeManagerName(String manager) {
        if (!StringUtils.hasText(manager)) {
            return null;
        }
        return realEstate.manager.name.like("%" + manager + "%");
    }

    private BooleanExpression likeTeamName(String teamName) {
        if (!StringUtils.hasText(teamName)) {
            return null;
        }
        return realEstate.manager.team.name.like("%" + teamName + "%");
    }

}
