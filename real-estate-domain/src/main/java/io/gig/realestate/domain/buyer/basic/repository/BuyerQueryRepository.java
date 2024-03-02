package io.gig.realestate.domain.buyer.basic.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.buyer.basic.QBuyer.buyer;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(eqProcessCdId(condition.getProcessCd()));
        where.and(likeTitle(condition.getTitle()));
        where.and(likeName(condition.getName()));
        where.and(likeManagerName(condition.getManagerName()));

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

    private BooleanExpression eqProcessCdId(Long processCdId) {
        return processCdId != null ? buyer.processCd.id.eq(processCdId) : null;
    }

    private BooleanExpression likeTitle(String title) {
        return StringUtils.hasText(title) ? buyer.title.like("%" + title + "%") : null;
    }

    private BooleanExpression likeManagerName(String managerName) {
        return StringUtils.hasText(managerName) ? buyer.updatedBy.name.like("%" + managerName + "%") : null;
    }

    private BooleanExpression likeName(String name) {
        return StringUtils.hasText(name) ? buyer.name.like("%" + name + "%") : null;
    }
}
