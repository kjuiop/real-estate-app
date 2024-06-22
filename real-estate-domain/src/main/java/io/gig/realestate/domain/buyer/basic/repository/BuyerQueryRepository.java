package io.gig.realestate.domain.buyer.basic.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorRole;
import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.basic.types.CompleteType;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.buyer.basic.QBuyer.buyer;
import static io.gig.realestate.domain.buyer.manager.QBuyerManager.buyerManager;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition, Administrator loginUser) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(adminStatusNotWithdraw());
        where.and(ownManager(loginUser));
        where.and(likeTitle(condition.getTitle()));
        where.and(likePreferArea(condition.getPreferArea()));
        where.and(likePreferSubway(condition.getPreferSubway()));
        where.and(likeCustomerName(condition.getCustomerName()));
        where.and(likeManagerName(condition.getManagerName()));
        where.and(likePurposeCds(condition.getPurposeCds()));
        where.and(likeBuyerGradeCds(condition.getBuyerGradeCds()));
        where.and(betweenSuccessPercent(condition.getMinSuccessPercent(), condition.getMaxSuccessPercent()));
        where.and(betweenSalePrice(condition.getMinSalePrice(), condition.getMaxSalePrice()));
        where.and(betweenLandAreaPy(condition.getMinLandAreaPy(), condition.getMaxLandAreaPy()));
        where.and(betweenTotalAreaPy(condition.getMinTotalAreaPy(), condition.getMaxTotalAreaPy()));
        where.and(betweenExclusiveAreaPy(condition.getMinExclusiveAreaPy(), condition.getMaxExclusiveAreaPy()));
        where.and(betweenCreatedAt(condition.getBeforeCreatedAt(), condition.getAfterCreatedAt()));

        JPAQuery<BuyerListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(BuyerListDto.class,
                        buyer))
                .from(buyer)
                .where(where)
                .orderBy(buyer.id.desc())
                .limit(condition.getPageableWithSort().getPageSize())
                .offset(condition.getPageableWithSort().getOffset());

        List<BuyerListDto> content = contentQuery.fetch();

        Long total = queryFactory
                .select(buyer.count())
                .from(buyer)
                .where(where)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, condition.getPageableWithSort(), total);
    }

    public List<BuyerDetailDto> getBuyerProcessingList() {

        JPAQuery<BuyerDetailDto> contentQuery = this.queryFactory
                .select(Projections.constructor(BuyerDetailDto.class,
                        buyer))
                .from(buyer)
                .where(defaultCondition())
                .where(eqCompleteType(CompleteType.Proceeding))
                .where(afterTwoWeeksUpdated())
                ;

        return contentQuery.fetch();
    }

    public List<BuyerListDto> getBuyerListByLoginUserId(Administrator loginUser) {

        JPAQuery<BuyerListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(BuyerListDto.class,
                        buyer
                ))
                .from(buyer)
                .where(defaultCondition())
                .where(ownManager(loginUser))
                ;

        return contentQuery.fetch();
    }

    public Optional<Buyer> getBuyerById(Long buyerId) {
        Buyer fetch = queryFactory
                .selectFrom(buyer)
                .where(defaultCondition())
                .where(eqBuyerId(buyerId))
                .limit(1)
                .fetchOne();
        return Optional.ofNullable(fetch);
    }

    public Optional<BuyerDetailDto> getBuyerDetail(Long buyerId) {

        BuyerDetailDto buyerDetail = queryFactory
                .select(Projections.constructor(BuyerDetailDto.class,
                        buyer
                        ))
                .from(buyer)
                .where(defaultCondition())
                .where(eqBuyerId(buyerId))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(buyerDetail);
    }

    private BooleanExpression defaultCondition() {
        return buyer.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return buyerId != null ? buyer.id.eq(buyerId) : null;
    }

    private BooleanExpression likeTitle(String title) {
        return StringUtils.hasText(title) ? buyer.title.like("%" + title + "%") : null;
    }

    private BooleanExpression likePurposeCds(String purposeCds) {
        if (!StringUtils.hasText(purposeCds)) {
            return null;
        }

        BooleanExpression predicate = null;
        String[] array = purposeCds.split(",");
        for (String str : array) {
            if (predicate == null) {
                predicate = buyer.purposeCds.like("%" + str + "%");
            } else {
                predicate = predicate.or(buyer.purposeCds.like("%" + str + "%"));
            }
        }

        return predicate;
    }

    private BooleanExpression likeBuyerGradeCds(String buyerGradeCds) {
        if (!StringUtils.hasText(buyerGradeCds)) {
            return null;
        }

        BooleanExpression predicate = null;
        String[] array = buyerGradeCds.split(",");
        for (String str : array) {
            if (predicate == null) {
                predicate = buyer.buyerGradeCds.like("%" + str + "%");
            } else {
                predicate = predicate.or(buyer.buyerGradeCds.like("%" + str + "%"));
            }
        }

        return predicate;
    }

    private BooleanExpression likePreferArea(String preferArea) {
        return StringUtils.hasText(preferArea) ? buyer.preferArea.like("%" + preferArea + "%") : null;
    }

    private BooleanExpression likePreferSubway(String preferSubway) {
        return StringUtils.hasText(preferSubway) ? buyer.preferSubway.like("%" + preferSubway + "%") : null;
    }

    private BooleanExpression likeCustomerName(String customerName) {
        return StringUtils.hasText(customerName) ? buyer.customerName.like("%" + customerName + "%") : null;
    }

    private BooleanExpression likeManagerName(String managerName) {
        return StringUtils.hasText(managerName) ? buyer.createdBy.name.like("%" + managerName + "%") : null;
    }

    private BooleanExpression betweenSuccessPercent(Integer minSuccessPercent, Integer maxSuccessPercent) {
        if (minSuccessPercent == null || maxSuccessPercent == null || minSuccessPercent < 0 || maxSuccessPercent < 0 || maxSuccessPercent < minSuccessPercent) {
            return null;
        }

        return buyer.id.in(
                JPAExpressions.selectDistinct(buyer.id)
                        .from(buyer)
                        .where(buyer.successPercent.between(minSuccessPercent, maxSuccessPercent))
        );
    }

    private BooleanExpression betweenSalePrice(Integer minSalePrice, Integer maxSalePrice) {
        if (minSalePrice == null || maxSalePrice == null || minSalePrice < 0 || maxSalePrice < 0 || maxSalePrice < minSalePrice) {
            return null;
        }

        return buyer.id.in(
                JPAExpressions.selectDistinct(buyer.id)
                        .from(buyer)
                        .where(buyer.salePrice.between(minSalePrice, maxSalePrice))
        );
    }

    private BooleanExpression betweenLandAreaPy(Integer minLandAreaPy, Integer maxLandAreaPy) {
        if (minLandAreaPy == null || maxLandAreaPy == null || minLandAreaPy < 0 || maxLandAreaPy < 0 || maxLandAreaPy < minLandAreaPy) {
            return null;
        }

        return buyer.id.in(
                JPAExpressions.selectDistinct(buyer.id)
                        .from(buyer)
                        .where(buyer.landAreaPy.between(minLandAreaPy, maxLandAreaPy))
        );
    }

    private BooleanExpression betweenTotalAreaPy(Integer minTotalAreaPy, Integer maxTotalAreaPy) {
        if (minTotalAreaPy == null || maxTotalAreaPy == null || minTotalAreaPy < 0 || maxTotalAreaPy < 0 || maxTotalAreaPy < minTotalAreaPy) {
            return null;
        }

        return buyer.id.in(
                JPAExpressions.selectDistinct(buyer.id)
                        .from(buyer)
                        .where(buyer.totalAreaPy.between(minTotalAreaPy, maxTotalAreaPy))
        );
    }

    private BooleanExpression betweenExclusiveAreaPy(Integer minExclusiveAreaPy, Integer maxExclusiveAreaPy) {
        if (minExclusiveAreaPy == null || maxExclusiveAreaPy == null || minExclusiveAreaPy < 0 || maxExclusiveAreaPy < 0 || maxExclusiveAreaPy < minExclusiveAreaPy) {
            return null;
        }

        return buyer.id.in(
                JPAExpressions.selectDistinct(buyer.id)
                        .from(buyer)
                        .where(buyer.exclusiveAreaPy.between(minExclusiveAreaPy, maxExclusiveAreaPy))
        );
    }

    private BooleanExpression betweenCreatedAt(LocalDateTime beforeCreatedAt, LocalDateTime afterCreatedAt) {
        if (beforeCreatedAt == null || afterCreatedAt == null) {
            return null;
        }

        return buyer.createdAt.between(beforeCreatedAt, afterCreatedAt);
    }

    private BooleanExpression ownManager(Administrator loginUser) {
        for (AdministratorRole adminRole : loginUser.getAdministratorRoles()) {
            if (adminRole.getRole().getName().equals("ROLE_SUPER_ADMIN")) {
                return null;
            }
        }

        return buyer.id.in(
                JPAExpressions.selectDistinct(buyerManager.buyer.id)
                        .from(buyerManager)
                        .where(buyerManager.buyer.eq(buyer))
                        .where(buyerManager.admin.id.eq(loginUser.getId()))
                        .where(buyerManager.deleteYn.eq(YnType.N)));
    }

    private BooleanExpression eqCompleteType(CompleteType completeType) {
        return buyer.completeType.eq(completeType);
    }

    private BooleanExpression afterTwoWeeksUpdated() {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        return buyer.updatedAt.before(twoWeeksAgo);
    }

    private BooleanExpression adminStatusNotWithdraw() {
        return buyer.createdBy.status.ne(AdminStatus.WITHDRAW)
                .and(buyer.createdBy.deleteYn.eq(YnType.N))
                ;
    }
}
