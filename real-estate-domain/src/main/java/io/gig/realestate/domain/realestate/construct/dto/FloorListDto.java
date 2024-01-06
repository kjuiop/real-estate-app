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

    // 대장상 면적
    private double area;

    // 대장상 면적 평
    private double areaPy;

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

    public FloorListDto (FloorPriceInfo f) {
        this.flrNo = f.getFlrNo();
        this.flrNoNm = f.getFlrNoNm();
        this.roomName = f.getRoomName();
        this.area = f.getArea();
        this.areaPy = calculatePy(f.getArea());
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
    }

    private static double calculatePy(double area) {
        if (area == 0) {
            return 0.0;
        }

        double result = area / 3.305785;
        result = Math.round(result * 100.0) / 100.0;
        return result;
    }
}
