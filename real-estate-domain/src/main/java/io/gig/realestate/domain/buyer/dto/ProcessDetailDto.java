package io.gig.realestate.domain.buyer.dto;

import io.gig.realestate.domain.buyer.BuyerDetail;
import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/02/25
 */

@Getter
@SuperBuilder
public class ProcessDetailDto {

    private String processCd;

    private String name;

    private String title;

    private String inflowPath;

    private String adAddress;

    private String adManager;

    private YnType fakeYn;

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

    private YnType companyEstablishAtYn;

    private String investmentCharacterCd;

    public ProcessDetailDto(BuyerDetail d) {
        this.processCd = d.getProcessCd().getCode();
        this.name = d.getName();
        this.title = d.getTitle();
        this.inflowPath = d.getInflowPath();
        this.adAddress = d.getAdAddress();
        this.adManager = d.getAdManager();
        this.fakeYn = d.getFakeYn();
        this.minSalePrice = d.getMinSalePrice();
        this.maxSalePrice = d.getMaxSalePrice();
        this.handCache = d.getHandCache();
        this.customerSector = d.getCustomerSector();
        this.customerPosition = d.getCustomerPosition();
        this.customerName = d.getCustomerName();
        this.purchasePoint = d.getPurchasePoint();
        this.preferArea = d.getPreferArea();
        this.preferSubway = d.getPreferSubway();
        this.preferRoad = d.getPreferRoad();
        this.exclusiveAreaPy = d.getExclusiveAreaPy();
        this.moveYear = d.getMoveYear();
        this.moveMonth = d.getMoveMonth();
        this.deliveryWay = d.getDeliveryWay();
        this.nextPromise = d.getNextPromise();
        this.requestDetail = d.getRequestDetail();
        this.usageTypeCds = d.getUsageTypeCds();
        this.companyEstablishAtYn = d.getCompanyEstablishAtYn();
        if (d.getInvestmentCharacterCd() != null) {
            this.investmentCharacterCd = d.getInvestmentCharacterCd().getCode();
        }
    }
}
