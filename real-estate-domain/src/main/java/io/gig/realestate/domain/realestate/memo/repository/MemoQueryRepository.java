package io.gig.realestate.domain.realestate.memo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import io.gig.realestate.domain.realestate.memo.dto.MemoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.realestate.land.QLandInfo.landInfo;
import static io.gig.realestate.domain.realestate.memo.QMemoInfo.memoInfo;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemoListDto> getMemoListInfoByRealEstateId(Long realEstateId) {

        JPAQuery<MemoListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(MemoListDto.class,
                        memoInfo
                ))
                .from(memoInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .orderBy(memoInfo.id.desc());

        return contentQuery.fetch();
    }

    private BooleanExpression defaultCondition() {
        return memoInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? memoInfo.realEstate.id.eq(realEstateId) : null;
    }
}
