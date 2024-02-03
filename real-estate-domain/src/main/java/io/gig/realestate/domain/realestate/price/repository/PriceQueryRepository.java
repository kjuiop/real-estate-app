package io.gig.realestate.domain.realestate.price.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.price.PriceInfo;
import io.gig.realestate.domain.realestate.price.dto.PriceListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.realestate.price.QPriceInfo.priceInfo;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriceQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PriceListDto> getPriceInfoByRealEstateId(Long realEstateId) {

        JPAQuery<PriceListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(PriceListDto.class,
                        priceInfo
                ))
                .from(priceInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .orderBy(priceInfo.id.asc());

        return contentQuery.fetch();
    }

    public PriceInfo getPriceInfoById(Long priceId) {

        JPAQuery<PriceInfo> contentQuery = this.queryFactory
                .selectFrom(priceInfo)
                .where(defaultCondition())
                .where(eqPriceId(priceId))
                .orderBy(priceInfo.id.asc());

        return contentQuery.fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return priceInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? priceInfo.realEstate.id.eq(realEstateId) : null;
    }

    private BooleanExpression eqPriceId(Long priceId) {
        return priceId != null ? priceInfo.id.eq(priceId) : null;
    }
}
