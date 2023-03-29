package io.gig.realestate.domain.category;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
public interface CategoryReader {
    Optional<Category> findById(Long id);
}
