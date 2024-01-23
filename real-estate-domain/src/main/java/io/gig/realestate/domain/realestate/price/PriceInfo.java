package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceUpdateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double salePrice;

    private double depositPrice;

    private double revenueRate;

    private double averageUnitPrice;

    private double guaranteePrice;

    private double rentMonth;

    private double management;

    private double managementExpense;

    @Builder.Default
    @Column(columnDefinition = "int default '0'")
    private double landPyungUnitPrice = 0;

    @Builder.Default
    @Column(columnDefinition = "int default '0'")
    private double buildingPyungUnitPrice = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static PriceInfo create(PriceCreateForm createForm, RealEstate realEstate) {
        PriceInfo priceInfo = PriceInfo.builder()
                .id(createForm.getPriceId())
                .salePrice(createForm.getSalePrice())
                .depositPrice(createForm.getDepositPrice())
                .revenueRate(createForm.getRevenueRate())
                .averageUnitPrice(createForm.getAverageUnitPrice())
                .guaranteePrice(createForm.getGuaranteePrice())
                .rentMonth(createForm.getRentMonth())
                .management(createForm.getManagement())
                .managementExpense(createForm.getManagementExpense())
                .realEstate(realEstate)
                .build();

        priceInfo.landPyungUnitPrice = calculateLandPyungUnit(priceInfo.getSalePrice(), createForm.getTotalLndpclArByPyung());
        priceInfo.averageUnitPrice = calculateLandPyungUnit(priceInfo.getSalePrice(), createForm.getTotalLndpclArByPyung());
        priceInfo.buildingPyungUnitPrice = calculateBuildingPyungUnit(priceInfo.getSalePrice(), createForm.getTotAreaByPyung());
        return priceInfo;
    }

    public static PriceInfo update(PriceCreateForm createForm, RealEstate realEstate) {
        PriceInfo priceInfo = PriceInfo.builder()
                .salePrice(createForm.getSalePrice())
                .depositPrice(createForm.getDepositPrice())
                .revenueRate(createForm.getRevenueRate())
                .averageUnitPrice(createForm.getAverageUnitPrice())
                .guaranteePrice(createForm.getGuaranteePrice())
                .rentMonth(createForm.getRentMonth())
                .management(createForm.getManagement())
                .managementExpense(createForm.getManagementExpense())
                .realEstate(realEstate)
                .build();

        priceInfo.landPyungUnitPrice = calculateLandPyungUnit(priceInfo.getSalePrice(), createForm.getTotalLndpclArByPyung());
        priceInfo.averageUnitPrice = calculateLandPyungUnit(priceInfo.getSalePrice(), createForm.getTotalLndpclArByPyung());
        priceInfo.buildingPyungUnitPrice = calculateBuildingPyungUnit(priceInfo.getSalePrice(), createForm.getTotAreaByPyung());
        return priceInfo;
    }

    public static PriceInfo createByUpload(double salePrice, RealEstate realEstate) {
        return PriceInfo.builder()
                .salePrice(salePrice)
                .realEstate(realEstate)
                .build();
    }

    public void calculatePyung(double salePrice, int totalLndpclArByPyung, int totalTotAreaByPyung) {
        this.landPyungUnitPrice = calculateLandPyungUnit(salePrice, totalLndpclArByPyung);
        this.buildingPyungUnitPrice = calculateBuildingPyungUnit(salePrice, totalTotAreaByPyung);
    }

    private static int calculateLandPyungUnit(double salePrice, double lndpclAr) {
        if (salePrice == 0.0 || lndpclAr == 0.0) {
            return 0;
        }
        salePrice *= 10000;
        double pyungUnit = salePrice / lndpclAr;
        return (int) Math.round(pyungUnit);
    }

    private static int calculateBuildingPyungUnit(double salePrice, double totArea) {
        if (salePrice == 0.0 || totArea == 0.0) {
            return 0;
        }
        salePrice *= 10000;
        double pyungUnit = salePrice / totArea;
        return (int) Math.round(pyungUnit);
    }


}
