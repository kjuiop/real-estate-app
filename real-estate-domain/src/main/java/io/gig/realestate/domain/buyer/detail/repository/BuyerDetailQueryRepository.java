package io.gig.realestate.domain.buyer.detail.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.buyer.detail.QBuyerDetail.buyerDetail;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerDetailQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<BuyerDetail> getBuyerDetailByBuyerIdAndProcessCd(Long buyerId, Long processCd) {
        BuyerDetail fetch = queryFactory
                .selectFrom(buyerDetail)
                .where(defaultCondition())
                .where(eqBuyerId(buyerId))
                .where(eqProcessCdId(processCd))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(fetch);
    }

    private BooleanExpression defaultCondition() {
        return buyerDetail.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return buyerId != null ? buyerDetail.buyer.id.eq(buyerId) : null;
    }

    private BooleanExpression eqProcessCdId(Long processCdId) {
        return processCdId != null ? buyerDetail.processCd.id.eq(processCdId) : null;
    }

}
