package io.gig.realestate.domain.realestate.landprice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.landprice.LandPriceInfo;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.realestate.landprice.QLandPriceInfo.landPriceInfo;

/**
 * @author : JAKE
 * @date : 2023/12/25
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LandPriceQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<LandPriceListDto> getLandPriceInfoByRealEstateId(Long realEstateId) {

        JPAQuery<LandPriceListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(LandPriceListDto.class,
                        landPriceInfo
                        ))
                .from(landPriceInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                ;

        return contentQuery.fetch();
    }

    public LandPriceInfo getLandPRiceInfoById(Long landPriceId) {

        JPAQuery<LandPriceInfo> contentQuery = this.queryFactory
                .selectFrom(landPriceInfo)
                .where(defaultCondition())
                .where(eqLandPriceId(landPriceId));

        return contentQuery.fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return landPriceInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqLandPriceId(Long landPriceId) {
        return landPriceId != null ? landPriceInfo.id.eq(landPriceId) : null;
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? landPriceInfo.realEstate.id.eq(realEstateId) : null;
    }
}
