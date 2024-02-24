package io.gig.realestate.domain.buyer.dto;

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

    private Long investmentCharacterCd;

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
