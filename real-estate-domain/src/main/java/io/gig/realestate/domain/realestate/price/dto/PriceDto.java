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

    private double landPyungUnitPrice;

    private double buildingPyungUnitPrice;

    public PriceDto(PriceInfo p) {
        this.priceId = p.getId();
        if (p.getRealEstate() != null) {
            this.realEstateId = p.getRealEstate().getId();
        }
        this.salePrice = p.getSalePrice();
        this.priceAdjuster = p.getPriceAdjuster();
        this.landUnitPrice = p.getLandUnitPrice();
        this.totalAreaUnitPrice = p.getTotalAreaUnitPrice();
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
