package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
public interface PriceService {
    Long create(PriceCreateForm createForm, LoginUser loginUser);
}
