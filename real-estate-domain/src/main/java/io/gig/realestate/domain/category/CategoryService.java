package io.gig.realestate.domain.category;

import io.gig.realestate.domain.category.dto.CategoryCreateForm;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
public interface CategoryService {

    Long create(CategoryCreateForm createForm);

}
