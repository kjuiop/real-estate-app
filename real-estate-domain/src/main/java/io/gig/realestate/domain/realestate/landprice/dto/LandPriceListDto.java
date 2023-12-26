package io.gig.realestate.domain.realestate.landprice.dto;

import io.gig.realestate.domain.realestate.landprice.LandPriceInfo;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
public class LandPriceListDto extends LandPriceDto {

    public LandPriceListDto(LandPriceInfo l) {
        super(l);
    }
}
