package io.gig.realestate.domain.category.repository;

import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Component
@Transactional
@RequiredArgsConstructor
public class CategoryStoreImpl implements CategoryStore {

    private final CategoryStoreRepository categoryStoreRepository;

    @Override
    public Category store(Category category) {
        return categoryStoreRepository.save(category);
    }
}
