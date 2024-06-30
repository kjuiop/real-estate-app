package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.land.LandInfo;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public class RealEstateListDto extends RealEstateDto {

    public String managerName;
    public double salePrice;
    public double revenueRate;
    public double platArea;
    public double totArea;
    public double archArea;
    public String prposArea1Nm = "";
    public double landPyungUnitPrice;
    public double buildingPyungUnitPrice;
    public int roadWidth;
    public double lndpclAr;
    public double lndpclArByPyung;
    public double totAreaByPyung;
    public double archAreaByPyung;
    public Long realEstateId;

    public RealEstateListDto(RealEstate r) {
        super(r);
        if (r.getManagerBy() != null) {
            this.managerName = r.getManagerBy().getName();
        }
        if (r.getPriceInfoList().size() > 0) {
            this.salePrice = r.getPriceInfoList().get(0).getSalePrice();
            this.revenueRate = r.getPriceInfoList().get(0).getRevenueRate();
            this.landPyungUnitPrice = r.getPriceInfoList().get(0).getLandPyungUnitPrice();
            this.buildingPyungUnitPrice = r.getPriceInfoList().get(0).getBuildingPyungUnitPrice();
        }
        if (r.getConstructInfoList().size() > 0) {
            this.platArea = r.getConstructInfoList().get(0).getPlatArea();
            this.totArea = r.getConstructInfoList().get(0).getTotArea();
            this.totAreaByPyung = r.getConstructInfoList().get(0).getTotAreaByPyung();
            this.archArea = r.getConstructInfoList().get(0).getArchArea();
            this.archAreaByPyung = r.getConstructInfoList().get(0).getArchAreaByPyung();
        }
        if (r.getLandInfoList().size() > 0) {
            double lndpclAr = 0;
            double lndpclArPy = 0;
            for (int i=0; i<r.getLandInfoList().size(); i++) {
                LandInfo landInfo = r.getLandInfoList().get(i);
                if (landInfo.getRoadWidth() > 0 && i == 0) {
                    this.roadWidth += landInfo.getRoadWidth();
                }

                if (landInfo.getLndpclAr() > 0) {
                    lndpclAr += landInfo.getLndpclAr();
                }
                if (landInfo.getLndpclArByPyung() > 0) {
                    lndpclArPy += landInfo.getLndpclArByPyung();
                }
            }
            this.prposArea1Nm = r.getLandInfoList().get(0).getPrposArea1Nm();
//            lndpclAr = Math.round((lndpclAr * 100) / 100);
//            lndpclArPy = Math.round((lndpclArPy * 100) / 100);
            this.lndpclAr = lndpclAr;
            this.lndpclArByPyung = lndpclArPy;
        }
    }
}
