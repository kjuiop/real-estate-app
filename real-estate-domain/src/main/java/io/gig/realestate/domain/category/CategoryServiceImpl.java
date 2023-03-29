package io.gig.realestate.domain.category;

import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import io.gig.realestate.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
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
    @Transactional
    public Long create(@NotNull CategoryCreateForm dto) {
        Category newCategory = Category.create(dto);
        if (dto.existParentId()) {
            Category parent = getCategoryById(dto.getParentId());
            newCategory.addParent(parent);
        }

        return categoryStore.store(newCategory).getId();
    }


    public Category getCategoryById(Long id) {
        Optional<Category> foundCategory = categoryReader.findById(id);
        if (foundCategory.isEmpty()) {
            throw new NotFoundException(">>> Category Not Found");
        }

        return foundCategory.get();
    }
}
