package io.gig.realestate.domain.realestate.customer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Override
    @Transactional
    public Long create(CustomerCreateForm createForm, LoginUser loginUser) {
        return null;
    }
}
