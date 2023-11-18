package io.gig.realestate.domain.realestate.print.dto;

import io.gig.realestate.domain.realestate.print.PrintInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/11/12
 */
@Getter
@Setter
public class PrintDto {

    private String propertyImgUrl;

    private String buildingImgUrl;

    private String locationImgUrl;

    private String landDecreeImgUrl;

    private String developPlanImgUrl;

    public PrintDto (PrintInfo p) {
        this.propertyImgUrl = p.getPropertyImgUrl();
        this.buildingImgUrl = p.getBuildingImgUrl();
        this.locationImgUrl = p.getLocationImgUrl();
        this.landDecreeImgUrl = p.getLandDecreeImgUrl();
        this.developPlanImgUrl = p.getDevelopPlanImgUrl();
    }
}
