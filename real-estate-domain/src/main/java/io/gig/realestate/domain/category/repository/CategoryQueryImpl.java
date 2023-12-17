package io.gig.realestate.domain.category.repository;

import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryReader;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.exception.NotFoundException;
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
        return queryRepository.getChildrenCategoryDtos(parentId);
    }

    @Override
    public List<CategoryDto> getChildrenCategoryDtosByName(String name) {
        return queryRepository.getChildrenCategoryDtosByName(name);
    }

    @Override
    public long getCountCategoryData() {
        return queryRepository.getCountCategoryData();
    }

    @Override
    public Optional<CategoryDto> getCategoryDtoById(Long id) {
        return queryRepository.getCategoryDtoById(id);
    }

    @Override
    public CategoryDto getCategoryDtoByName(String name) {
        Optional<CategoryDto> foundCategory = queryRepository.getCategoryDtoByName(name);
        if (foundCategory.isEmpty()) {
            throw new NotFoundException(">>> 카테고리가 존재하지 않습니다.");
        }
        return foundCategory.get();
    }

    @Override
    public Category getCategoryByCode(String code) {
        Optional<Category> foundCategory = queryRepository.getCategoryByCode(code);
        if (foundCategory.isEmpty()) {
            throw new NotFoundException(">>> 카테고리가 존재하지 않습니다.");
        }
        return foundCategory.get();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return queryRepository.findById(id);
    }

}
