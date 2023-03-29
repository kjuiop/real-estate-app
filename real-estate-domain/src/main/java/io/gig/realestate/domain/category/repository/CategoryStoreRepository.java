package io.gig.realestate.domain.category.repository;

import io.gig.realestate.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Repository
public interface CategoryStoreRepository extends JpaRepository<Category, Long> {
}
