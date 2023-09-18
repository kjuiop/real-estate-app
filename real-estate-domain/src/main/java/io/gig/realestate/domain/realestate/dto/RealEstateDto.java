package io.gig.realestate.domain.realestate.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.RealEstate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class RealEstateDto {

    private Long realEstateId;

    private YnType ownYn;

    public RealEstateDto(RealEstate r) {
        this.realEstateId = r.getId();
        this.ownYn = r.getOwnYn();
    }
}
