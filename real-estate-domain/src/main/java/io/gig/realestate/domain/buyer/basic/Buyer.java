package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.basic.dto.BuyerCreateForm;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/02/18
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Buyer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;

    private String usageTypeCds;

    @Lob
    private String preferBuildingCds;

    @Lob
    private String purposeCds;

    @Lob
    private String loanCharacterCds;

    @Lob
    private String investmentTimingCds;

    private int successPercent;

    private String inflowPath;

    private String adAddress;

    private String adManager;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType fakeYn = YnType.N;

    private double minSalePrice;

    private double maxSalePrice;

    private double handCache;

    private String customerSector;

    private String customerPosition;

    private String customerName;

    private String purchasePoint;

    private String preferArea;

    private String preferSubway;

    private String preferRoad;

    private double exclusiveAreaPy;

    private String moveYear;

    private String moveMonth;

    private String deliveryWay;

    private String nextPromise;

    private String requestDetail;

    private String investmentCharacterCds;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType companyEstablishAtYn = YnType.N;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CompanyScaleType companyScale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static Buyer create(BuyerCreateForm createForm, Administrator loginUser) {
        return Buyer.builder()
                .title(createForm.getTitle())
                .name(createForm.getName())
                .purposeCds(createForm.getPurposeCds())
                .loanCharacterCds(createForm.getLoanCharacterCds())
                .preferBuildingCds(createForm.getPreferBuildingCds())
                .investmentTimingCds(createForm.getInvestmentTimingCds())
                .usageTypeCds(createForm.getUsageTypeCds())
                .successPercent(createForm.getSuccessPercent())
                .inflowPath(createForm.getInflowPath())
                .adAddress(createForm.getAdAddress())
                .adManager(createForm.getAdManager())
                .fakeYn(createForm.getFakeYn())
                .minSalePrice(createForm.getMinSalePrice())
                .maxSalePrice(createForm.getMaxSalePrice())
                .handCache(createForm.getHandCache())
                .customerSector(createForm.getCustomerSector())
                .customerPosition(createForm.getCustomerPosition())
                .customerName(createForm.getCustomerName())
                .purchasePoint(createForm.getPurchasePoint())
                .preferArea(createForm.getPreferArea())
                .preferSubway(createForm.getPreferSubway())
                .preferRoad(createForm.getPreferRoad())
                .exclusiveAreaPy(createForm.getExclusiveAreaPy())
                .moveYear(createForm.getMoveYear())
                .moveMonth(createForm.getMoveMonth())
                .deliveryWay(createForm.getDeliveryWay())
                .nextPromise(createForm.getNextPromise())
                .requestDetail(createForm.getRequestDetail())
                .investmentCharacterCds(createForm.getInvestmentCharacterCds())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void update(LoginUser loginUser) {
        this.updatedBy = loginUser.getLoginUser();
    }
}
