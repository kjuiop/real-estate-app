package io.gig.realestate.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.category.QCategory.category;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Category> findById(Long categoryId) {
        return Optional.ofNullable(
                this.queryFactory
                        .selectFrom(category)
                        .where(category.id.eq(categoryId))
                        .fetchOne());
    }

}
