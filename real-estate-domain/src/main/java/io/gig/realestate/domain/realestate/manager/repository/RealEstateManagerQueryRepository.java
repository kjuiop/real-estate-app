package io.gig.realestate.domain.realestate.manager.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.manager.RealEstateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.realestate.manager.QRealEstateManager.realEstateManager;

/**
 * @author : JAKE
 * @date : 2024/06/30
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealEstateManagerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<RealEstateManager> getRealEstateManager(RealEstate realEstate, Administrator manager) {
        return Optional.ofNullable(this.queryFactory
                .selectFrom(realEstateManager)
                .where(defaultCondition())
                .where(realEstateManager.realEstate.eq(realEstate))
                .where(realEstateManager.admin.eq(manager))
                .limit(1)
                .fetchFirst()
        );
    }

    private BooleanExpression defaultCondition() {
        return realEstateManager.deleteYn.eq(YnType.N);
    }
}
