package io.gig.realestate.domain.buyer.maps.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.buyer.maps.QBuyerHistoryMap.buyerHistoryMap;


/**
 * @author : JAKE
 * @date : 2024/04/04
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerHistoryMapQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<HistoryMapListDto> getHistoriesByBuyerId(Long buyerId) {
        return this.queryFactory
                .select(Projections.constructor(HistoryMapListDto.class,
                        buyerHistoryMap))
                .from(buyerHistoryMap)
                .where(defaultCondition())
                .where(eqBuyerId(buyerId))
                .orderBy(buyerHistoryMap.createdAt.desc())
                .fetch()
                ;
    }

    private BooleanExpression defaultCondition() {
        return buyerHistoryMap.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return buyerId != null ? buyerHistoryMap.buyer.id.eq(buyerId) : null;
    }

}
