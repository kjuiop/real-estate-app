package io.gig.realestate.domain.realestate.landusage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.landusage.LandUsageInfo;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static io.gig.realestate.domain.realestate.landusage.QLandUsageInfo.landUsageInfo;

/**
 * @author : JAKE
 * @date : 2024/01/23
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LandUsageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LandUsageDto getLandUsageInfoByRealEstateId(Long realEstateId) {

        JPAQuery<LandUsageDto> contentQuery = this.queryFactory
                .select(Projections.constructor(LandUsageDto.class,
                        landUsageInfo
                        ))
                .from(landUsageInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                ;

        return contentQuery.fetchFirst();
    }

    public LandUsageInfo getLandUsageInfoById(Long landUsageId) {

        JPAQuery<LandUsageInfo> contentQuery = this.queryFactory
                .selectFrom(landUsageInfo)
                .from(landUsageInfo)
                .where(defaultCondition())
                .where(eqLandUsageId(landUsageId))
                ;

        return contentQuery.fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return landUsageInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? landUsageInfo.realEstate.id.eq(realEstateId) : null;
    }

    private BooleanExpression eqLandUsageId(Long landUsageId) {
        return landUsageId != null ? landUsageInfo.id.eq(landUsageId) : null;
    }

}
