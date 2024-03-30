package io.gig.realestate.domain.buyer.detail.dto;

import io.gig.realestate.domain.buyer.detail.BuyerDetail;
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

    private Long buyerDetailId;

    private String processCd;

    private String name;

    private int successPercent;

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

    private Long investmentCharacterCd;

    public ProcessDetailDto() {
        this.fakeYn = YnType.N;
        this.companyEstablishAtYn = YnType.N;
    }

    public ProcessDetailDto(BuyerDetail d) {
    }
}
