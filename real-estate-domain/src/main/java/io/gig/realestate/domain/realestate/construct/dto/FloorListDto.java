package io.gig.realestate.domain.realestate.construct.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/10/07
 */
@Getter
@NoArgsConstructor
public class FloorListDto {

    private int flrNo;

    private String flrNoNm;

    private String roomName;

    // 면적
    private Double area;

    private double lndpclAr;

    private double lndpclArByPyung;

    private String companyName;

    // 주용도
    private String mainPurpsCdNm;

    // 부용도
    private String etcPurps;

    private int guaranteePrice;

    private int rent;

    private int management;

    private String termStartDate;

    private String termEndDate;

    private String etcInfo;

    private YnType underFloorYn;

    public FloorListDto (FloorPriceInfo f) {
        this.flrNo = f.getFlrNo();
        this.flrNoNm = f.getFlrNoNm();
        this.roomName = f.getRoomName();
        this.area = f.getArea();
        this.lndpclAr = f.getLndpclAr();
        this.lndpclArByPyung = f.getLndpclArByPyung();
        this.mainPurpsCdNm = f.getMainPurpsCdNm();
        this.etcPurps = f.getEtcPurps();
        this.companyName = f.getCompanyName();
        this.guaranteePrice = f.getGuaranteePrice();
        this.rent = f.getRent();
        this.management = f.getManagement();
        this.termStartDate = f.getTermStartDate();
        this.termEndDate = f.getTermEndDate();
        this.etcInfo = f.getEtcInfo();
        this.underFloorYn = f.getUnderFloorYn();
    }
}
