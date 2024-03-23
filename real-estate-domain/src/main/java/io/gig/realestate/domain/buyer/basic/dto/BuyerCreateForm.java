package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Getter
@Setter
public class BuyerCreateForm {

    private Long buyerId;

    private String title;

    private String name;

    private String preferBuildingCds;

    private String purposeCds;

    private String loanCharacterCds;

    private String investmentTimingCds;

    private int successPercent;

    @NotNull
    private Long processCd;

    private String adAddress;

    private String adManager;

    private String requestDetail;

    private YnType companyEstablishAtYn;

    private String customerName;

    private String customerPosition;

    private String customerSector;

    private String deliveryWay;

    private double exclusiveAreaPy;

    private YnType fakeYn;

    private double handCache;

    private String inflowPath;

    private String investmentCharacterCds;

    private double maxSalePrice;

    private double minSalePrice;

    private String moveYear;

    private String moveMonth;

    private String nextPromise;

    private String preferArea;

    private String preferRoad;

    private String preferSubway;

    private String purchasePoint;

    private int sortOrder;

    private String usageTypeCds;


}
