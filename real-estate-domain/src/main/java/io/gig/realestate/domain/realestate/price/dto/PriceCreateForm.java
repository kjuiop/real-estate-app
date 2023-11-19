package io.gig.realestate.domain.realestate.price.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Getter
@Setter
public class PriceCreateForm {

    private double salePrice;

    private double depositPrice;

    private double revenueRate;

    private int averageUnitPrice;

    private int guaranteePrice;

    private int rentMonth;

    private int management;

    private int managementExpense;

    private double totalLndpclArByPyung;

    private double totArea;
}