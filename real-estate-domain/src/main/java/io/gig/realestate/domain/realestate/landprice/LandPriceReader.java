package io.gig.realestate.domain.realestate.landprice;

import io.gig.realestate.domain.realestate.landprice.dto.LandPriceListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/25
 */
public interface LandPriceReader {
    List<LandPriceListDto> getLandPriceInfoByRealEstateId(Long realEstateId);
}
