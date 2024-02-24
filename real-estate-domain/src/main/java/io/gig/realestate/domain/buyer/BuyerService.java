package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.dto.BuyerCreateForm;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerService {
    Long create(BuyerCreateForm createForm, LoginUser loginUser);
}
