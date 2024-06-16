package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.buyer.basic.types.CompleteType;
import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/02/18
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class BuyerDto {

    private Long buyerId;

    private String buyerGradeCds;

    private int successPercent;

    private String title;

    private String customerName;

    private String customerPhone;

    private String inflowPath;

    private double salePrice;

    private double handCache;

    private double landAreaPy;

    private double totalAreaPy;

    private double exclusiveAreaPy;

    private String purposeCds;

    private String loanCharacterCds;

    private String preferBuildingCds;

    private String investmentTimingCds;

    private String preferArea;

    private String preferSubway;

    private String preferRoad;

    private String moveYear;

    private String moveMonth;

    private YnType companyEstablishAtYn;

    private CompanyScaleType companyScale;

    private String requestDetail;

    private String managerName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CompleteType completeType;

    public BuyerDto(Buyer b) {
        this.buyerId = b.getId();
        this.buyerGradeCds = b.getBuyerGradeCds();
        this.successPercent = b.getSuccessPercent();
        this.title = b.getTitle();
        this.customerName = b.getCustomerName();
        this.customerPhone = b.getCustomerPhone();
        this.inflowPath = b.getInflowPath();
        this.salePrice = b.getSalePrice();
        this.handCache = b.getHandCache();
        this.landAreaPy = b.getLandAreaPy();
        this.totalAreaPy = b.getTotalAreaPy();
        this.exclusiveAreaPy = b.getExclusiveAreaPy();
        this.purposeCds = b.getPurposeCds();
        this.loanCharacterCds = b.getLoanCharacterCds();
        this.preferBuildingCds = b.getPreferBuildingCds();
        this.investmentTimingCds = b.getInvestmentTimingCds();
        this.preferArea = b.getPreferArea();
        this.preferSubway = b.getPreferSubway();
        this.preferRoad = b.getPreferRoad();
        this.moveYear = b.getMoveYear();
        this.moveMonth = b.getMoveMonth();
        this.companyEstablishAtYn = b.getCompanyEstablishAtYn();
        this.companyScale = b.getCompanyScale();
        this.requestDetail = b.getRequestDetail();
        this.completeType = b.getCompleteType();
        this.createdAt = b.getCreatedAt();
        this.updatedAt = b.getUpdatedAt();
    }
}
