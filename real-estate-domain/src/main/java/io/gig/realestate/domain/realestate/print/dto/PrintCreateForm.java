package io.gig.realestate.domain.realestate.print.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/11/12
 */
@Getter
@Setter
public class PrintCreateForm {

    private String propertyImgUrl;

    private String buildingImgUrl;

    private String locationImgUrl;

    private String landDecreeImgUrl;

    private String developPlanImgUrl;
}
