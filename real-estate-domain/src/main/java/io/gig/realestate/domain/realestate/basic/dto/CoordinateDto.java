package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/12/06
 */
@Getter
@Setter
public class CoordinateDto {

    private String address;

    public CoordinateDto(RealEstate r) {
        this.address = r.getAddress();
    }
}
