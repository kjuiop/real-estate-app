package io.gig.realestate.domain.buyer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.buyer.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.buyer.QBuyer.buyer;

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

    private BooleanExpression defaultCondition() {
        return buyer.deleteYn.eq(YnType.N);
    }

}
