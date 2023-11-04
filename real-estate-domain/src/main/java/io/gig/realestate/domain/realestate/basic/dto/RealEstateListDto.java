package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.land.LandInfo;
import org.springframework.util.StringUtils;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public class RealEstateListDto extends RealEstateDto {

    public String managerName;
    public int salePrice;
    public double revenueRate;
    public double platArea;
    public double totArea;
    public double archArea;
    public String prposArea1Nm = "";
    public int landPyungUnitPrice;
    public int buildingPyungUnitPrice;
    public int roadWidth;

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
            StringBuilder prposArea1Nm = new StringBuilder();
            for (int i=0; i<r.getLandInfoList().size(); i++) {
                LandInfo landInfo = r.getLandInfoList().get(i);
                if (StringUtils.hasText(landInfo.getPrposArea1Nm())) {
                    prposArea1Nm.append(landInfo.getPrposArea1Nm());
                }
                if (i < r.getLandInfoList().size()-1) {
                    prposArea1Nm.append(", ");
                }

                if (landInfo.getRoadWidth() != null && landInfo.getRoadWidth() > 0 && i == 0) {
                    this.roadWidth += landInfo.getRoadWidth();
                }
            }
            this.prposArea1Nm = prposArea1Nm.toString();
        }
    }
}
