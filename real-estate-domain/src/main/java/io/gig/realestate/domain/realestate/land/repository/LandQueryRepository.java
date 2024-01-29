package io.gig.realestate.domain.realestate.land.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.realestate.land.QLandInfo.landInfo;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LandQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<LandListDto> getLandListDtoByRealEstateId(Long realEstateId) {

        JPAQuery<LandListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(LandListDto.class,
                        landInfo
                        ))
                .from(landInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .orderBy(landInfo.id.asc());

        return contentQuery.fetch();
    }

    public Optional<LandInfo> getLandInfoByLandId(Long landId) {

        Optional<LandInfo> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(landInfo)
                .where(eqLandId(landId))
                .fetchOne());

        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return landInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? landInfo.realEstate.id.eq(realEstateId) : null;
    }

    private BooleanExpression eqLandId(Long landId) {
        return landId != null ? landInfo.id.eq(landId) : null;
    }
}
