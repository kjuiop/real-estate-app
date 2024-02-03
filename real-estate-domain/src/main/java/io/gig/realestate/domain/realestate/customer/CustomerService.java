package io.gig.realestate.domain.realestate.customer;

import io.gig.realestate.domain.realestate.customer.dto.CustomerDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
public interface CustomerService {

    List<CustomerDto> getCustomerListInfoByRealEstateId(Long realEstateId);

    CustomerInfo getCustomerInfoById(Long customerId);
}
