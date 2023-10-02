package io.gig.realestate.domain.realestate.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Getter
@Setter
public class CustomerCreateForm {

    private Long realEstateId;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private List<CustomerDto> customerInfo;
}
