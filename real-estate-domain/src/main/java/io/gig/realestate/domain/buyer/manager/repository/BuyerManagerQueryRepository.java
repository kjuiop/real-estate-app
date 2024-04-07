package io.gig.realestate.domain.buyer.manager.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.buyer.manager.QBuyerManager.buyerManager;

/**
 * @author : JAKE
 * @date : 2024/04/07
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerManagerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<BuyerManager> getBuyerManager(Buyer buyer, Administrator manager) {
        Optional<BuyerManager> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(buyerManager)
                .where(defaultCondition())
                .where(buyerManager.buyer.eq(buyer))
                .where(buyerManager.admin.eq(manager))
                .limit(1)
                .fetchFirst());
        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return buyerManager.deleteYn.eq(YnType.N);
    }


}
