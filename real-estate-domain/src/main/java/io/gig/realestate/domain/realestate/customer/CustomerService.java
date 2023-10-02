package io.gig.realestate.domain.realestate.customer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
public interface CustomerService {
    Long create(CustomerCreateForm createForm, LoginUser loginUser);
}
