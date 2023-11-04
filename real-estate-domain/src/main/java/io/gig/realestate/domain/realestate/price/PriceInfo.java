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

    private int salePrice;

    private int depositPrice;

    private double revenueRate;

    private int averageUnitPrice;

    private int guaranteePrice;

    private int rentMonth;

    private int management;

    private int managementExpense;

    @Builder.Default
    @Column(columnDefinition = "int default '0'")
    private int landPyungUnitPrice = 0;

    @Builder.Default
    @Column(columnDefinition = "int default '0'")
    private int buildingPyungUnitPrice = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static PriceInfo create(PriceCreateForm createForm, RealEstate realEstate) {
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
        priceInfo.buildingPyungUnitPrice = calculateBuildingPyungUnit(priceInfo.getSalePrice(), createForm.getTotArea());
        return priceInfo;
    }

    private static int calculateLandPyungUnit(int salePrice, double lndpclAr) {
        if (salePrice == 0 || lndpclAr == 0.0) {
            return 0;
        }
        salePrice *= 10000;
        return (int) (salePrice / lndpclAr);
    }

    private static int calculateBuildingPyungUnit(int salePrice, double totArea) {
        if (salePrice == 0 || totArea == 0.0) {
            return 0;
        }
        salePrice *= 10000;
        return (int) (salePrice / totArea);
    }
}
