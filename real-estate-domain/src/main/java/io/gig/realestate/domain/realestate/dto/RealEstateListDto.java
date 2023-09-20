package io.gig.realestate.domain.realestate.dto;

import io.gig.realestate.domain.realestate.RealEstate;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public class RealEstateListDto extends RealEstateDto {

    public String managerName;

    public RealEstateListDto(RealEstate r) {
        super(r);
        if (r.getManager() != null) {
            this.managerName = r.getManager().getName();
        }
    }
}
