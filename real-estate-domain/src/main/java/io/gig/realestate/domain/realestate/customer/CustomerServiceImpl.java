package io.gig.realestate.domain.realestate.customer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.customer.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerReader customerReader;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getCustomerListInfoByRealEstateId(Long realEstateId) {
        return customerReader.getCustomerListInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerInfo getCustomerInfoById(Long customerId) {
        return customerReader.getCustomerById(customerId);
    }
}
