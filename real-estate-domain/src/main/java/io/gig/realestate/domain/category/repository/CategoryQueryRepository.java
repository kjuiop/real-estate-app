package io.gig.realestate.domain.category.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<CategoryDto> getParentCategoryDtos() {
        List<CategoryDto> fetch = this.queryFactory
                .selectDistinct(Projections.constructor(CategoryDto.class, category))
                .from(category)
                .where(defaultCondition())
                .where(parentIsNull())
                .orderBy(category.sortOrder.asc())
                .fetch();
        return fetch;
    }

    public List<CategoryDto> getChildrenCategoryDtos(Long parentId) {
        List<CategoryDto> fetch = this.queryFactory.selectDistinct(Projections.constructor(CategoryDto.class, category))
                .from(category)
                .where(defaultCondition())
                .where(category.parent.id.eq(parentId))
                .orderBy(category.sortOrder.asc())
                .fetch();

        return fetch;
    }

    public Optional<Category> findById(Long categoryId) {
        return Optional.ofNullable(
                this.queryFactory
                        .selectFrom(category)
                        .where(defaultCondition())
                        .where(category.id.eq(categoryId))
                        .fetchOne());
    }

    private BooleanExpression parentIsNull() {
        return category.parent.isNull();
    }

    public Optional<CategoryDto> getCategoryDtoById(Long id) {
        Optional<CategoryDto> fetch = Optional.ofNullable(this.queryFactory
                .select(Projections.constructor(CategoryDto.class, category))
                .from(category)
                .where(defaultCondition())
                .where(category.id.eq(id))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    private BooleanExpression defaultCondition() {
        return category.deleteYn.eq(YnType.N);
    }
}
