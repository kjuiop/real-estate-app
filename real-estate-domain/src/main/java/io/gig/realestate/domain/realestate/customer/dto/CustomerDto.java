package io.gig.realestate.domain.realestate.customer.dto;

import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.customer.types.CustomerType;
import io.gig.realestate.domain.realestate.customer.types.GenderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    private Long customerId;

    private CustomerType type;

    private String birth;

    private String customerName;

    private String phone;

    private String etcPhone;

    private GenderType gender;

    private String etcInfo;

    private String companyName;

    private String companyPhone;

    private String representName;

    private String representPhone;

    public CustomerDto(CustomerInfo c) {
        this.customerId = c.getId();
        this.type = c.getType();
        this.customerName = c.getCustomerName();
        this.birth = c.getBirth();
        this.phone = c.getPhone();
        this.etcPhone = c.getEtcPhone();
        this.gender = c.getGender();
        this.etcInfo = c.getEtcInfo();
        this.companyName = c.getCompanyName();
        this.companyPhone = c.getCompanyPhone();
        this.representName = c.getRepresentName();
        this.representPhone = c.getRepresentPhone();
    }
}
