package io.gig.realestate.domain.buyer.realestate.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.realestate.dto.HistoryRealEstateDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.buyer.realestate.QHistoryRealEstate.historyRealEstate;

/**
 * @author : JAKE
 * @date : 2024/04/10
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryRealEstateQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<HistoryRealEstateDto> getHistoryRealEstateByHistoryId(Long historyId) {
        return this.queryFactory
                .select(Projections.constructor(HistoryRealEstateDto.class,
                        historyRealEstate))
                .from(historyRealEstate)
                .where(defaultCondition())
                .where(eqHistoryId(historyId))
                .fetch()
                ;
    }

    private BooleanExpression defaultCondition() {
        return historyRealEstate.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqHistoryId(Long historyId) {
        return historyId != null ? historyRealEstate.buyerHistory.id.eq(historyId) : null;
    }

}
