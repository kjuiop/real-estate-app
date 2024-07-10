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

    private Long priceId;

    private double salePrice;

    private double priceAdjuster;

    private double landUnitPrice;

    private double totalAreaUnitPrice;

    private double revenueRate;

    private double averageUnitPrice;

    private double guaranteePrice;

    private double rentMonth;

    private double management;

    private double managementExpense;

    private double totalLndpclArByPyung;

    private double totArea;

    private double totAreaByPyung;
}