package io.gig.realestate.domain.realestate.basic.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.QRealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.dto.*;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.category.QCategory.category;
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
                .orderBy(createOrderSpecifier(searchDto.getSortField()))
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

    public List<RealEstateListDto> getRealEstateByAddress(String address) {
        return this.queryFactory
                .selectDistinct(Projections.constructor(RealEstateListDto.class,
                            realEstate))
                .from(realEstate)
                .where(defaultCondition())
                .where(likeAddress(address))
                .orderBy(realEstate.id.desc())
                .fetch()
                ;
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
                .join(realEstate.usageType, category).fetchJoin()
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
                .where(defaultCondition())
                .where(realEstate.address.eq(address))
                .fetchOne();

        return result;
    }

    public Long isExistLegalCodeAndBunJi(String legalCode, String bun, String ji) {
        Long result = this.queryFactory
                .select(realEstate.count())
                .from(realEstate)
                .where(eqLegalCode(legalCode))
                .where(eqBun(bun))
                .where(eqJi(ji))
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

    public List<CoordinateDto> getCoordinateList(RealEstateSearchDto condition) {
        BooleanBuilder where = getSearchCondition(condition);

        return this.queryFactory
                .select(Projections.constructor(CoordinateDto.class, realEstate))
                .from(realEstate)
                .where(where)
                .orderBy(realEstate.createdAt.desc())
                .fetch();
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

    private BooleanExpression likeUsageCds(String usageCds) {
        if (!StringUtils.hasText(usageCds)) {
            return null;
        }

        BooleanExpression predicate = null;
        String[] array = usageCds.split(",");
        for (String str : array) {
            if (predicate == null) {
                predicate = realEstate.usageCds.like("%" + str + "%");
            } else {
                predicate = predicate.or(realEstate.usageCds.like("%" + str + "%"));
            }
        }

        return predicate;
    }

    private BooleanExpression eqRealEstateGradeCds(String realEstateGradeCds) {
        return StringUtils.hasText(realEstateGradeCds) ? realEstate.realEstateGradeCds.eq(realEstateGradeCds) : null;
    }

    private BooleanExpression eqExclusiveCds(String exclusiveCds) {
        return StringUtils.hasText(exclusiveCds) ? realEstate.exclusiveCds.eq(exclusiveCds) : null;
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
        return realEstate.managerBy.name.like("%" + manager + "%");
    }

    private BooleanExpression likeTeamName(String teamName) {
        if (!StringUtils.hasText(teamName)) {
            return null;
        }
        return realEstate.managerBy.team.name.like("%" + teamName + "%");
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

    private BooleanExpression betweenDepositPrice(Integer minDepositPrice, Integer maxDepositPrice) {
        if (minDepositPrice == null || maxDepositPrice == null || minDepositPrice < 0 || maxDepositPrice <= 0 || maxDepositPrice < minDepositPrice) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(priceInfo.realEstate.id)
                        .from(priceInfo)
                        .where(priceInfo.depositPrice.between(minDepositPrice, maxDepositPrice))
        );
    }

    private BooleanExpression betweenGuaranteePrice(Integer minGuaranteePrice, Integer maxGuaranteePrice) {
        if (minGuaranteePrice == null || maxGuaranteePrice == null || minGuaranteePrice < 0 || maxGuaranteePrice <= 0 || maxGuaranteePrice < minGuaranteePrice) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(priceInfo.realEstate.id)
                        .from(priceInfo)
                        .where(priceInfo.guaranteePrice.between(minGuaranteePrice, maxGuaranteePrice))
        );
    }

    private BooleanExpression betweenRentPrice(Integer minRentPrice, Integer maxRentPrice) {
        if (minRentPrice == null || maxRentPrice == null || minRentPrice < 0 || maxRentPrice <= 0 || maxRentPrice < minRentPrice) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(priceInfo.realEstate.id)
                        .from(priceInfo)
                        .where(priceInfo.rentMonth.between(minRentPrice, maxRentPrice))
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

    private BooleanExpression betweenLndpclArByPyung(Integer minLnpclArByPyung, Integer maxLnpclArByPyung) {
        if (minLnpclArByPyung == null || maxLnpclArByPyung == null || minLnpclArByPyung < 0 || maxLnpclArByPyung <= 0 || maxLnpclArByPyung < minLnpclArByPyung) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(landInfo.realEstate.id)
                        .from(landInfo)
                        .where(landInfo.lndpclArByPyung.between(minLnpclArByPyung, maxLnpclArByPyung))
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

    private BooleanExpression betweenArchAreaByPyung(Integer minArchAreaByPyung, Integer maxArchAreaByPyung) {
        if (minArchAreaByPyung == null || maxArchAreaByPyung == null || minArchAreaByPyung < 0 || maxArchAreaByPyung <= 0 || maxArchAreaByPyung < minArchAreaByPyung) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(constructInfo.realEstate.id)
                        .from(constructInfo)
                        .where(constructInfo.archAreaByPyung.between(minArchAreaByPyung, maxArchAreaByPyung))
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

    private BooleanExpression betweenTotAreaByPyung(Integer minTotAreaByPyung, Integer maxTotAreaByPyung) {
        if (minTotAreaByPyung == null || maxTotAreaByPyung == null || minTotAreaByPyung < 0 || maxTotAreaByPyung <= 0 || maxTotAreaByPyung < minTotAreaByPyung) {
            return null;
        }

        return realEstate.id.in(
                JPAExpressions.selectDistinct(constructInfo.realEstate.id)
                        .from(constructInfo)
                        .where(constructInfo.totAreaByPyung.between(minTotAreaByPyung, maxTotAreaByPyung))
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

    private BooleanExpression betweenLandPriceDiff(Double minLandPriceDiff, Double maxLandPriceDiff) {
        if (minLandPriceDiff == null || maxLandPriceDiff == null || minLandPriceDiff < 0 || maxLandPriceDiff <= 0 || maxLandPriceDiff < minLandPriceDiff) {
            return null;
        }

        return realEstate.landPriceDiff.between(minLandPriceDiff, maxLandPriceDiff);
    }

    private BooleanExpression betweenYearBuiltAt(LocalDate afterYearBuiltAt) {
        if (afterYearBuiltAt == null) {
            return null;
        }

        return realEstate.yearBuiltAt.after(afterYearBuiltAt);
    }

    private BooleanExpression betweenRemodelingAt(LocalDate afterRemodelingAt) {
        if (afterRemodelingAt == null) {
            return null;
        }

        return realEstate.remodelingAt.after(afterRemodelingAt);
    }

    private BooleanExpression betweenUpdatedAt(LocalDateTime beforeUpdatedAt, LocalDateTime afterUpdatedAt) {
        if (beforeUpdatedAt == null || afterUpdatedAt == null) {
            return null;
        }

        return realEstate.updatedAt.between(beforeUpdatedAt, afterUpdatedAt);
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

    private BooleanExpression eqLegalCode(String legalCode) {
        if (!StringUtils.hasText(legalCode)) {
            return null;
        }

        return realEstate.legalCode.eq(legalCode);
    }

    private BooleanExpression likeProcessTypeCds(List<ProcessType> processTypeCds) {
        if (processTypeCds.isEmpty()) {
            return null;
        }
        return realEstate.processType.in(processTypeCds);
    }

//    private BooleanExpression likeUsageCds(String usageCds) {
//        if (!StringUtils.hasText(usageCds)) {
//            return null;
//        }
//
//        BooleanExpression predicate = null;
//        String[] array = usageCds.split(",");
//        for (String str : array) {
//            if (predicate == null) {
//                predicate = realEstate.usageCds.like("%" + str + "%");
//            } else {
//                predicate = predicate.or(realEstate.usageCds.like("%" + str + "%"));
//            }
//        }
//
//        return predicate;
//    }

    private BooleanExpression likeAddress(String address) {
        return StringUtils.hasText(address) ? realEstate.address.like("%" + address + "%") : null;
    }

    private BooleanBuilder getSearchCondition(RealEstateSearchDto searchDto) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(likeProcessTypeCds(searchDto.getProcessTypeCds()));
        where.and(eqSido(searchDto.getSido()));
        where.and(eqGungu(searchDto.getGungu()));
        where.and(eqDong(searchDto.getDong()));
        where.and(eqLandType(searchDto.getLandType()));
        where.and(eqBun(searchDto.getBun()));
        where.and(eqJi(searchDto.getJi()));
        where.and(likeUsageCds(searchDto.getUsageCds()));
        where.and(likeBuildingName(searchDto.getBuildingName()));
        where.and(eqRealEstateId(searchDto.getRealEstateId()));
        where.and(likePrposArea1Nm(searchDto.getPrposArea1Nm()));
        where.and(likeManagerName(searchDto.getManager()));
        where.and(likeTeamName(searchDto.getTeam()));
        where.and(eqRealEstateGradeCds(searchDto.getRealEstateGradeCds()));
        where.and(eqExclusiveCds(searchDto.getExclusiveCds()));
        where.and(betweenSalePrice(searchDto.getMinSalePrice(), searchDto.getMaxSalePrice()));
        where.and(betweenDepositPrice(searchDto.getMinDepositPrice(), searchDto.getMaxDepositPrice()));
        where.and(betweenGuaranteePrice(searchDto.getMinGuaranteePrice(), searchDto.getMaxGuaranteePrice()));
        where.and(betweenRentPrice(searchDto.getMinRentPrice(), searchDto.getMaxRentPrice()));
        where.and(betweenArchArea(searchDto.getMinArchArea(), searchDto.getMaxArchArea()));
        where.and(betweenArchAreaByPyung(searchDto.getMinArchAreaByPyung(), searchDto.getMaxArchAreaByPyung()));
        where.and(betweenLndpclAr(searchDto.getMinLndpclAr(), searchDto.getMaxLndpclAr()));
        where.and(betweenLndpclArByPyung(searchDto.getMinLndpclArByPyung(), searchDto.getMaxLndpclArByPyung()));
        where.and(betweenTotArea(searchDto.getMinTotArea(), searchDto.getMaxTotArea()));
        where.and(betweenTotAreaByPyung(searchDto.getMinTotAreaByPyung(), searchDto.getMaxTotAreaByPyung()));
        where.and(betweenRevenueRate(searchDto.getMinRevenueRate(), searchDto.getMaxRevenueRate()));
        where.and(betweenYearBuiltAt(searchDto.getAfterYearBuiltAt()));
        where.and(betweenRemodelingAt(searchDto.getAfterRemodelingAt()));
        where.and(betweenUpdatedAt(searchDto.getBeforeUpdatedAt(), searchDto.getAfterUpdatedAt()));

        where.and(likeCustomerName(searchDto.getCustomer()));
        where.and(eqPhone(searchDto.getPhone()));
        where.and(betweenLandPriceDiff(searchDto.getMinLandPriceDiff(), searchDto.getMaxLandPriceDiff()));

        // exclusiveCds
//        where.and(betweenRoadWidth(searchDto.getMinRoadWidth(), searchDto.getMaxRoadWidth()));
        return where;
    }

    private OrderSpecifier createOrderSpecifier(String sortField) {

        if (!StringUtils.hasText(sortField)) {
            return new OrderSpecifier(Order.DESC, realEstate.createdAt);
        }

        if (sortField.equals("createdAt")) {
            return new OrderSpecifier(Order.DESC, realEstate.createdAt);
        }

        if (sortField.equals("updatedAt")) {
            return new OrderSpecifier(Order.DESC, realEstate.updatedAt);
        }

        if (sortField.equals("salePrice")) {
            return new OrderSpecifier(Order.DESC, realEstate.salePrice);
        }

        return null;
    }
}
