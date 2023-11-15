package io.gig.realestate.domain.realestate.price.dto;

import io.gig.realestate.domain.realestate.price.PriceInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class PriceDto {

    private Long realEstateId;

    private Long priceId;

    private int salePrice;

    private int depositPrice;

    private double revenueRate;

    private int averageUnitPrice;

    private int guaranteePrice;

    private int rentMonth;

    private int management;

    private int managementExpense;

    private double landPyungUnitPrice;

    private double buildingPyungUnitPrice;

    public PriceDto(PriceInfo p) {
        this.priceId = p.getId();
        if (p.getRealEstate() != null) {
            this.realEstateId = p.getRealEstate().getId();
        }
        this.salePrice = p.getSalePrice();
        this.depositPrice = p.getDepositPrice();
        this.revenueRate = p.getRevenueRate();
        this.averageUnitPrice = p.getAverageUnitPrice();
        this.guaranteePrice = p.getGuaranteePrice();
        this.rentMonth = p.getRentMonth();
        this.management = p.getManagement();
        this.managementExpense = p.getManagementExpense();
        this.landPyungUnitPrice = p.getLandPyungUnitPrice();
        this.buildingPyungUnitPrice = p.getBuildingPyungUnitPrice();
    }
}
