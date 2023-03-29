package io.gig.realestate.domain.category.repository;

import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryReader;
import io.gig.realestate.domain.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryImpl implements CategoryReader {

    private final CategoryQueryRepository queryRepository;

    @Override
    public List<CategoryDto> getParentCategoryDtos() {
        return queryRepository.getParentCategoryDtos();
    }

    @Override
    public List<CategoryDto> getChildrenCategoryDtos(Long parentId) {
        return  queryRepository.getChildrenCategoryDtos(parentId);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return queryRepository.findById(id);
    }


}
