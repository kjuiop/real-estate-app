package io.gig.realestate.domain.realestate.price.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Getter
@Setter
public class PriceCreateForm {

    private Long realEstateId;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private int salePrice;

    private int depositPrice;

    private int revenueRate;

    private int averageUnitPrice;

    private int guaranteePrice;

    private int rentMonth;

    private int management;

    private int managementExpense;
}
