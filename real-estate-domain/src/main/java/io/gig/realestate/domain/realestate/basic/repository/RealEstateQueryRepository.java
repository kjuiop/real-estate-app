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
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailAllDto;
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
import static io.gig.realestate.domain.realestate.construct.QConstructInfo.constructInfo;
import static io.gig.realestate.domain.realestate.customer.QCustomerInfo.customerInfo;
import static io.gig.realestate.domain.realestate.land.QLandInfo.landInfo;
import static io.gig.realestate.domain.realestate.price.QPriceInfo.priceInfo;

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

        BooleanBuilder where = getSearchCondition(searchDto);

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

    public List<Long> getRealEstateIdsBySearch(RealEstateSearchDto searchDto) {

        BooleanBuilder where = getSearchCondition(searchDto);

        return this.queryFactory
                .select(realEstate.id)
                .from(realEstate)
                .where(where)
                .orderBy(realEstate.id.desc())
                .fetch();
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

    public Optional<RealEstateDetailAllDto> getRealEstateAllInfo(Long realEstateId) {

        RealEstateDetailAllDto realEstateDetailDto = queryFactory
                .select(Projections.constructor(RealEstateDetailAllDto.class,
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

    public Long isExistAddress(String address) {
        Long result = this.queryFactory
                .select(realEstate.count())
                .from(realEstate)
                .where(realEstate.address.eq(address))
                .fetchOne();

        return result;
    }

    public Optional<RealEstate> getPrevRealEstateId(Long currentId) {
        Optional<RealEstate> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(realEstate)
                .where(defaultCondition())
                .where(realEstate.id.lt(currentId))
                .orderBy(realEstate.id.desc())
                .fetchFirst());

        return fetch;
    }

    public Optional<RealEstate> getNextRealEstateId(Long currentId) {
        Optional<RealEstate> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(realEstate)
                .where(defaultCondition())
                .where(realEstate.id.gt(currentId))
                .orderBy(realEstate.id.asc())
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

    private BooleanExpression eqUsageTypeId(Long usageTypeId) {
        return usageTypeId != null ? realEstate.usageType.id.eq(usageTypeId) : null;
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

    private BooleanExpression betweenSalePrice(Integer minSalePrice, Integer maxSalePrice) {
        if (minSalePrice == null || maxSalePrice == null || minSalePrice < 0 || maxSalePrice <= 0 || maxSalePrice < minSalePrice) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(priceInfo.realEstate.id)
                        .from(priceInfo)
                        .where(priceInfo.salePrice.between(minSalePrice, maxSalePrice))
        );
    }

    private BooleanExpression betweenLndpclAr(Integer minLnpclAr, Integer maxLnpclAr) {
        if (minLnpclAr == null || maxLnpclAr == null || minLnpclAr < 0 || maxLnpclAr <= 0 || maxLnpclAr < minLnpclAr) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(landInfo.realEstate.id)
                        .from(landInfo)
                        .where(landInfo.lndpclAr.between(minLnpclAr, maxLnpclAr))
        );
    }

    private BooleanExpression betweenArchArea(Integer minArchArea, Integer maxArchArea) {
        if (minArchArea == null || maxArchArea == null || minArchArea < 0 || maxArchArea <= 0 || maxArchArea < minArchArea) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(constructInfo.realEstate.id)
                        .from(constructInfo)
                        .where(constructInfo.archArea.between(minArchArea, maxArchArea))
        );
    }

    private BooleanExpression betweenTotArea(Integer minTotArea, Integer maxTotArea) {
        if (minTotArea == null || maxTotArea == null || minTotArea < 0 || maxTotArea <= 0 || maxTotArea < minTotArea) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(constructInfo.realEstate.id)
                        .from(constructInfo)
                        .where(constructInfo.totArea.between(minTotArea, maxTotArea))
        );
    }

    private BooleanExpression betweenRevenueRate(Integer minRevenueRate, Integer maxRevenueRate) {
        if (minRevenueRate == null || maxRevenueRate == null || minRevenueRate < 0 || maxRevenueRate <= 0 || maxRevenueRate < minRevenueRate) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(priceInfo.realEstate.id)
                        .from(priceInfo)
                        .where(priceInfo.revenueRate.between(minRevenueRate, maxRevenueRate))
        );
    }

    private BooleanExpression betweenUseAprDay(Integer startUseAprDay, Integer endUseAprDay) {
        if (startUseAprDay == null || endUseAprDay == null || startUseAprDay < 0 || endUseAprDay <= 0 || endUseAprDay < startUseAprDay) {
            return null;
        }

        startUseAprDay *= 10000;
        endUseAprDay *= 10000;

        return realEstate.id.in(
                JPAExpressions.selectDistinct(constructInfo.realEstate.id)
                        .from(constructInfo)
                        .where(constructInfo.useAprDay.between(startUseAprDay, endUseAprDay))
        );
    }

    private BooleanExpression betweenRoadWidth(Integer minRoadWidth, Integer maxRoadWidth) {
        if (minRoadWidth == null || maxRoadWidth == null || minRoadWidth < 0 || maxRoadWidth <= 0 || maxRoadWidth < minRoadWidth) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(landInfo.realEstate.id)
                        .from(landInfo)
                        .where(landInfo.roadWidth.between(minRoadWidth, maxRoadWidth))
        );
    }


    private BooleanExpression likeCustomerName(String customer) {
        if (!StringUtils.hasText(customer)) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(customerInfo.realEstate.id)
                        .from(customerInfo)
                        .where(customerInfo.customerName.like("%" + customer + "%")
                                .or(customerInfo.companyName.like("%" + customer + "%"))));
    }

    private BooleanExpression eqPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(customerInfo.realEstate.id)
                        .from(customerInfo)
                        .where(customerInfo.phone.eq(phone)));
    }

    private BooleanBuilder getSearchCondition(RealEstateSearchDto searchDto) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(eqUsageTypeId(searchDto.getUsageCd()));
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
        where.and(betweenSalePrice(searchDto.getMinSalePrice(), searchDto.getMaxSalePrice()));
        where.and(betweenArchArea(searchDto.getMinArchArea(), searchDto.getMaxArchArea()));
        where.and(betweenLndpclAr(searchDto.getMinLndpclAr(), searchDto.getMaxLndpclAr()));
        where.and(betweenTotArea(searchDto.getMinTotArea(), searchDto.getMaxTotArea()));
        where.and(betweenRevenueRate(searchDto.getMinRevenueRate(), searchDto.getMaxRevenueRate()));
        where.and(betweenUseAprDay(searchDto.getStartUseAprDay(), searchDto.getEndUseAprDay()));
        where.and(likeCustomerName(searchDto.getCustomer()));
        where.and(eqPhone(searchDto.getPhone()));
        where.and(betweenRoadWidth(searchDto.getMinRoadWidth(), searchDto.getMaxRoadWidth()));
        return where;
    }
}
