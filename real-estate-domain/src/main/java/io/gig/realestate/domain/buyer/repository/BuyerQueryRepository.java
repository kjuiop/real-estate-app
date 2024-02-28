package io.gig.realestate.domain.buyer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.Buyer;
import io.gig.realestate.domain.buyer.BuyerDetail;
import io.gig.realestate.domain.buyer.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.buyer.QBuyer.buyer;
import static io.gig.realestate.domain.buyer.QBuyerDetail.buyerDetail;

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

    public Optional<BuyerDetail> getBuyerDetailByIdAndProcessCd(Long buyerId, Long processCdId) {
        BuyerDetail fetch = queryFactory
                .selectFrom(buyerDetail)
                .where(defaultCondition())
                .where(eqBuyerId(buyerId))
                .where(eqProcessCdId(processCdId))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(fetch);
    }

    private BooleanExpression defaultCondition() {
        return buyer.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return buyerId != null ? buyer.id.eq(buyerId) : null;
    }

    private BooleanExpression eqProcessCdId(Long processCdId) {
        return processCdId != null ? buyerDetail.processCd.id.eq(processCdId) : null;
    }
}
