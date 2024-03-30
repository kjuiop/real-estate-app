package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Getter
@Setter
public class BuyerForm {

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
}
