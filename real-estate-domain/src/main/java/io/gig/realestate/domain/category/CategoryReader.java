package io.gig.realestate.domain.category;

import io.gig.realestate.domain.category.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
public interface CategoryReader {
    Optional<Category> findById(Long id);

    List<CategoryDto> getParentCategoryDtos();

    List<CategoryDto> getChildrenCategoryDtos(Long parentId);

    Optional<CategoryDto> getCategoryDtoById(Long id);

    long getCountCategoryData();

    List<CategoryDto> getChildrenCategoryDtosByName(String name);

    CategoryDto getCategoryDtoByName(String name);
}
