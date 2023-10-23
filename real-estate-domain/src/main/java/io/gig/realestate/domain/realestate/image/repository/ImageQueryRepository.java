package io.gig.realestate.domain.realestate.image.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.realestate.image.QImageInfo.imageInfo;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ImageDto> getSubImageInfoByRealEstateId(Long realEstateId) {

        JPAQuery<ImageDto> contentQuery = this.queryFactory
                .select(Projections.constructor(ImageDto.class,
                        imageInfo
                ))
                .from(imageInfo)
                .where(eqRealEstateId(realEstateId))
                .orderBy(imageInfo.id.asc())
                ;

        return contentQuery.fetch();
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? imageInfo.realEstate.id.eq(realEstateId) : null;
    }
}
