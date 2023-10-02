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

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    @Override
    @Transactional
    public Long create(CustomerCreateForm createForm, LoginUser loginUser) {

        RealEstate realEstate;
        if (createForm.getRealEstateId() == null) {
            realEstate = RealEstate.initialInfo(createForm.getLegalCode(), createForm.getAddress(), createForm.getLandType(), createForm.getBun(), createForm.getJi());
        } else {
            realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
        }

        realEstate.getCustomerInfoList().clear();
        for (CustomerDto dto : createForm.getCustomerInfo()) {
            CustomerInfo info = CustomerInfo.create(dto, realEstate);
            realEstate.addCustomerInfo(info);
        }

        return realEstateStore.store(realEstate).getId();
    }
}
