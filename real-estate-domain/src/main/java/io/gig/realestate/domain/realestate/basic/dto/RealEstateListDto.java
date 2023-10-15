package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.realestate.basic.RealEstate;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public class RealEstateListDto extends RealEstateDto {

    public String managerName;
    public int salePrice;
    public int revenueRate;
    public double platArea;
    public double totArea;
    public double archArea;
    public String prposArea1Nm;
    public int landPyungUnitPrice;
    public int buildingPyungUnitPrice;

    public RealEstateListDto(RealEstate r) {
        super(r);
        if (r.getManager() != null) {
            this.managerName = r.getManager().getName();
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
            this.archArea = r.getConstructInfoList().get(0).getArchArea();
        }
        if (r.getLandInfoList().size() > 0) {
            this.prposArea1Nm = r.getLandInfoList().get(0).getPrposArea1Nm();
        }
    }
}
