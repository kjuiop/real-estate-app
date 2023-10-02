package io.gig.realestate.domain.realestate.customer.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CustomerType {

    CUSTOMER("Customer", "남자"),

    COMPANY("Company", "여자");

    private String key;

    private String description;
}
