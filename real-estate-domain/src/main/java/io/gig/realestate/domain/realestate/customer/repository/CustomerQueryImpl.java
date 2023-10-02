package io.gig.realestate.domain.realestate.customer.repository;

import io.gig.realestate.domain.realestate.customer.CustomerReader;
import io.gig.realestate.domain.realestate.customer.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerQueryImpl implements CustomerReader {

    private final CustomerQueryRepository queryRepository;

    @Override
    public List<CustomerDto> getCustomerListInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getCustomerListInfoByRealEstateId(realEstateId);
    }
}
