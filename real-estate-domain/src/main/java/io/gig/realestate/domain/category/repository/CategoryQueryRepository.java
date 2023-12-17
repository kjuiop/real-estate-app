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
import org.springframework.util.StringUtils;

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
                .where(eqParentId(parentId))
                .orderBy(category.sortOrder.asc())
                .fetch();

        return fetch;
    }

    public List<CategoryDto> getChildrenCategoryDtosByName(String name) {
        List<CategoryDto> fetch = this.queryFactory.selectDistinct(Projections.constructor(CategoryDto.class, category))
                .from(category)
                .where(defaultCondition())
                .where(eqParentName(name))
                .orderBy(category.sortOrder.asc())
                .fetch();

        return fetch;
    }

    public Optional<Category> findById(Long categoryId) {
        return Optional.ofNullable(
                this.queryFactory
                        .selectFrom(category)
                        .where(defaultCondition())
                        .where(eqCategoryId(categoryId))
                        .fetchOne());
    }

    public Optional<CategoryDto> getCategoryDtoById(Long id) {
        Optional<CategoryDto> fetch = Optional.ofNullable(this.queryFactory
                .select(Projections.constructor(CategoryDto.class, category))
                .from(category)
                .where(defaultCondition())
                .where(eqCategoryId(id))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    public Optional<CategoryDto> getCategoryDtoByName(String name) {
        Optional<CategoryDto> fetch = Optional.ofNullable(this.queryFactory
                .select(Projections.constructor(CategoryDto.class, category))
                .from(category)
                .where(defaultCondition())
                .where(category.name.eq(name))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    public Optional<Category> getCategoryByCode(String code) {
        Optional<Category> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(category)
                .where(defaultCondition())
                .where(category.code.eq(code))
                .limit(1)
                .fetchFirst());
        return fetch;
    }

    public Long getCountCategoryData() {
        return this.queryFactory
                .select(category.count())
                .from(category)
                .fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return category.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqCategoryId(Long categoryId) {
        return categoryId != null ? category.id.eq(categoryId) : null;
    }

    private BooleanExpression eqParentId(Long parentId) {
        return parentId != null ? category.parent.id.eq(parentId) : null;
    }

    private BooleanExpression eqParentName(String name) {
        return StringUtils.hasText(name) ? category.parent.name.eq(name) : null;
    }

    private BooleanExpression parentIsNull() {
        return category.parent.isNull();
    }

}
