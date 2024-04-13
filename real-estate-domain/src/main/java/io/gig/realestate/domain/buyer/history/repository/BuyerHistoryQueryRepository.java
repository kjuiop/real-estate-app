package io.gig.realestate.domain.buyer.history.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.buyer.history.QBuyerHistory.buyerHistory;
import static io.gig.realestate.domain.buyer.realestate.QHistoryRealEstate.historyRealEstate;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerHistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<HistoryListDto> getHistoriesByBuyerId(Long buyerId) {
        return this.queryFactory
                .select(Projections.constructor(HistoryListDto.class,
                        buyerHistory))
                .from(buyerHistory)
                .where(defaultCondition())
                .where(eqBuyerId(buyerId))
                .orderBy(buyerHistory.createdAt.desc())
                .fetch();
    }

    private BooleanExpression defaultCondition() {
        return buyerHistory.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return buyerId != null ? buyerHistory.buyer.id.eq(buyerId) : null;
    }

}
