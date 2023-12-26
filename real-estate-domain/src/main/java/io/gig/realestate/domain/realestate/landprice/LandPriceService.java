package io.gig.realestate.domain.realestate.landprice;

import io.gig.realestate.domain.realestate.landprice.dto.LandPriceDataApiDto;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceListDto;

import java.io.IOException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
public interface LandPriceService {
    List<LandPriceDataApiDto> getLandPricePublicData(String legalCode, String landType, String bun, String ji) throws IOException;

    List<LandPriceListDto> getLandPriceListInfo(Long realEstateId);
}
