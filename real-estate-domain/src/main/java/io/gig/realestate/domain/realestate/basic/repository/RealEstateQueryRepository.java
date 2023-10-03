package io.gig.realestate.domain.realestate.basic.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.realestate.basic.QRealEstate.realEstate;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealEstateQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();

        JPAQuery<RealEstateListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(RealEstateListDto.class,
                        realEstate
                ))
                .from(realEstate)
                .where(where)
                .where(defaultCondition())
                .orderBy(realEstate.id.desc())
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        List<RealEstateListDto> content = contentQuery.fetch();
        long total = content.size();

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }

    public Optional<RealEstateDetailDto> getRealEstateDetail(Long realEstateId) {

        RealEstateDetailDto realEstateDetailDto = queryFactory
                .select(Projections.constructor(RealEstateDetailDto.class,
                        realEstate
                ))
                .from(realEstate)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(realEstateDetailDto);
    }



    public Optional<RealEstate> getRealEstateById(Long realEstateId) {
        Optional<RealEstate> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(realEstate)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return realEstate.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? realEstate.id.eq(realEstateId) : null;
    }
}
