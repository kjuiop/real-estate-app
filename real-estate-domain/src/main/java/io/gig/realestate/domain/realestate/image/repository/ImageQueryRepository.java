package io.gig.realestate.domain.realestate.image.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.image.ImageInfo;
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
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .orderBy(imageInfo.id.asc())
                ;

        return contentQuery.fetch();
    }

    public ImageInfo getImageInfoById(Long imageId) {

        JPAQuery<ImageInfo> contentQuery = this.queryFactory
                .selectFrom(imageInfo)
                .where(defaultCondition())
                .where(eqImageId(imageId));

        return contentQuery.fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return imageInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? imageInfo.realEstate.id.eq(realEstateId) : null;
    }

    private BooleanExpression eqImageId(Long imageId) {
        return imageId != null ? imageInfo.id.eq(imageId) : null;
    }
}
