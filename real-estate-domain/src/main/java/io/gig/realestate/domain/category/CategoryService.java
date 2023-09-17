package io.gig.realestate.domain.category;

import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.category.dto.CategoryUpdateForm;
import io.gig.realestate.domain.common.YnType;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
public interface CategoryService {

    Long create(CategoryCreateForm createForm);

    List<CategoryDto> getParentCategoryDtos();

    List<CategoryDto> getChildrenCategoryDtos(Long parentId);

    CategoryDto getCategoryDtoById(Long id);

    Long update(CategoryUpdateForm updateForm);

    Long delete(Long id);

    long getCountCategoryData();

    Category initCategory(String name, YnType activeYn, int level, int sortOrder);

    Category initChildCategory(String name, YnType activeYn, int level, int sortOrder, Category parentCategory);

    List<CategoryDto> getChildrenCategoryDtosByName(String name);

    CategoryDto getCategoryDtoWithChildrenByName(String name);
}
