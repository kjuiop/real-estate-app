package io.gig.realestate.domain.buyer.detail.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return null;
    }

    public Optional<ProcessDetailDto> getProcessDetailById(Long buyerId, Long processCd) {
        return null;
    }

    private BooleanExpression defaultCondition() {
        return null;
    }

    private BooleanExpression eqBuyerId(Long buyerId) {
        return null;
    }

    private BooleanExpression eqProcessCdId(Long processCdId) {
        return null;
    }
}
