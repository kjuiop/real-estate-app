package io.gig.realestate.domain.realestate.customer.dto;

import io.gig.realestate.domain.realestate.customer.types.CustomerType;
import io.gig.realestate.domain.realestate.customer.types.GenderType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Getter
@Setter
public class CustomerDto {

    private Long customerId;

    private CustomerType type;

    private String customerName;

    private String phone;

    private String etcPhone;

    private GenderType gender;

    private String etcInfo;

    private String companyName;

    private String companyPhone;

    private String representName;

    private String representPhone;
}
