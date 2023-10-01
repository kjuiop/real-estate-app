package io.gig.realestate.domain.realestate.construct.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.realestate.construct.QConstructInfo.constructInfo;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConstructQueryRepository {
    
    private final JPAQueryFactory queryFactory;


    public Optional<ConstructDto> getConstructInfoByRealEstateId(Long realEstateId) {

        ConstructDto constructDto = queryFactory
                .select(Projections.constructor(ConstructDto.class,
                        constructInfo
                ))
                .from(constructInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(constructDto);
    }

    private BooleanExpression defaultCondition() {
        return constructInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? constructInfo.realEstate.id.eq(realEstateId) : null;
    }
}
