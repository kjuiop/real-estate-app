package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.dto.BuyerCreateForm;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyerDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_cd_id")
    private Category processCd;

    private int sortOrder;

    private String name;

    private String title;

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

    private String usageTypeCds;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType companyEstablishAtYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_type_id")
    private Category investmentCharacterCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static BuyerDetail create(BuyerCreateForm createForm, Category processCd, Buyer buyer, Administrator loginUser) {
        return BuyerDetail.builder()
                .buyer(buyer)
                .name(createForm.getName())
                .sortOrder(createForm.getSortOrder())
                .title(createForm.getTitle())
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
                .usageTypeCds(createForm.getUsageTypeCds())
                .processCd(processCd)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void setProcessCd(Category processCd) {
        this.processCd = processCd;
    }
}
