package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.dto.BuyerCreateForm;
import io.gig.realestate.domain.buyer.dto.BuyerDetailUpdateForm;
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

    public static BuyerDetail create(BuyerCreateForm createForm, Category processCd, Category investCharacterCd, Buyer buyer, Administrator loginUser) {
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
                .investmentCharacterCd(investCharacterCd)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public static BuyerDetail create(BuyerDetailUpdateForm updateForm, Category processCd, Category investmentCharacterCd, Buyer buyer, Administrator loginUser) {
        return BuyerDetail.builder()
                .buyer(buyer)
                .name(updateForm.getName())
                .sortOrder(updateForm.getSortOrder())
                .title(updateForm.getTitle())
                .inflowPath(updateForm.getInflowPath())
                .adAddress(updateForm.getAdAddress())
                .adManager(updateForm.getAdManager())
                .fakeYn(updateForm.getFakeYn())
                .minSalePrice(updateForm.getMinSalePrice())
                .maxSalePrice(updateForm.getMaxSalePrice())
                .handCache(updateForm.getHandCache())
                .customerSector(updateForm.getCustomerSector())
                .customerPosition(updateForm.getCustomerPosition())
                .customerName(updateForm.getCustomerName())
                .purchasePoint(updateForm.getPurchasePoint())
                .preferArea(updateForm.getPreferArea())
                .preferSubway(updateForm.getPreferSubway())
                .preferRoad(updateForm.getPreferRoad())
                .exclusiveAreaPy(updateForm.getExclusiveAreaPy())
                .moveYear(updateForm.getMoveYear())
                .moveMonth(updateForm.getMoveMonth())
                .deliveryWay(updateForm.getDeliveryWay())
                .nextPromise(updateForm.getNextPromise())
                .requestDetail(updateForm.getRequestDetail())
                .usageTypeCds(updateForm.getUsageTypeCds())
                .processCd(processCd)
                .investmentCharacterCd(investmentCharacterCd)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void update(BuyerDetailUpdateForm updateForm, Category processCd, Category investmentCharacterCd, LoginUser loginUser) {
        this.name = updateForm.getName();
        this.title = updateForm.getTitle();
        this.inflowPath = updateForm.getInflowPath();
        this.adAddress = updateForm.getAdAddress();
        this.adManager = updateForm.getAdManager();
        this.fakeYn = updateForm.getFakeYn();
        this.minSalePrice = updateForm.getMinSalePrice();
        this.maxSalePrice = updateForm.getMaxSalePrice();
        this.handCache = updateForm.getHandCache();
        this.customerSector = updateForm.getCustomerSector();
        this.customerPosition = updateForm.getCustomerPosition();
        this.customerName = updateForm.getCustomerName();
        this.purchasePoint = updateForm.getPurchasePoint();
        this.preferArea = updateForm.getPreferArea();
        this.preferSubway = updateForm.getPreferSubway();
        this.preferRoad = updateForm.getPreferRoad();
        this.exclusiveAreaPy = updateForm.getExclusiveAreaPy();
        this.moveYear = updateForm.getMoveYear();
        this.moveMonth = updateForm.getMoveMonth();
        this.deliveryWay = updateForm.getDeliveryWay();
        this.nextPromise = updateForm.getNextPromise();
        this.requestDetail = updateForm.getRequestDetail();
        this.usageTypeCds = updateForm.getUsageTypeCds();
        this.processCd = processCd;
        this.investmentCharacterCd = investmentCharacterCd;
        this.updatedBy = loginUser.getLoginUser();
    }
}
