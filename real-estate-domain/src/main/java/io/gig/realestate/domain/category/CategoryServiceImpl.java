package io.gig.realestate.domain.category;

import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.category.dto.CategoryUpdateForm;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryReader categoryReader;
    private final CategoryStore categoryStore;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getParentCategoryDtos() {
        return categoryReader.getParentCategoryDtos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getChildrenCategoryDtos(Long parentId) {
        return categoryReader.getChildrenCategoryDtos(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getChildrenCategoryDtosByCode(String code) {
        return categoryReader.getChildrenCategoryDtosByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getChildrenCategoryDtosByName(String name) {
        return categoryReader.getChildrenCategoryDtosByName(name);
    }

    @Override
    public CategoryDto getCategoryDtoWithChildrenByName(String name) {
        CategoryDto categoryDto = categoryReader.getCategoryDtoByName(name);
        List<CategoryDto> children = categoryReader.getChildrenCategoryDtos(categoryDto.getId());
        categoryDto.addChildren(children);
        return categoryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryDtoById(Long id) {

        Optional<CategoryDto> foundCategory = categoryReader.getCategoryDtoById(id);
        if (foundCategory.isEmpty()) {
            throw new NotFoundException(">>> Category Not Found");
        }

        return foundCategory.get();
    }

    @Override
    @Transactional
    public Long create(@NotNull CategoryCreateForm dto) {
        Category newCategory = Category.create(dto);
        if (dto.existParentId()) {
            Category parent = getCategoryById(dto.getParentId());
            newCategory.addParent(parent);
        }

        return categoryStore.store(newCategory).getId();
    }

    @Override
    @Transactional
    public Long update(CategoryUpdateForm updateForm) {
        Category foundCategory = getCategoryById(updateForm.getId());
        foundCategory.update(updateForm);
        return categoryStore.store(foundCategory).getId();
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Category foundCategory = getCategoryById(id);
        foundCategory.delete();
        return categoryStore.store(foundCategory).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountCategoryData() {
        return categoryReader.getCountCategoryData();
    }

    @Override
    @Transactional
    public Category initCategory(String code, String name, YnType activeYn, int level, int sortOrder) {
        Category newCategory = Category.initCategory(code, name, activeYn, level, sortOrder);
        return categoryStore.store(newCategory);
    }

    @Override
    @Transactional
    public Category initChildCategory(String code, String parentCodePath, String name, YnType activeYn, int level, int sortOrder, Category parentCategory) {
        Category newCategory = Category.initChildCategory(code, parentCodePath, name, activeYn, level, sortOrder);
        newCategory.addParent(parentCategory);
        return categoryStore.store(newCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryByCode(String code) {
        return categoryReader.getCategoryByCode(code);
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        Optional<Category> foundCategory = categoryReader.findById(id);
        if (foundCategory.isEmpty()) {
            throw new NotFoundException(">>> Category Not Found");
        }

        return foundCategory.get();
    }
}
