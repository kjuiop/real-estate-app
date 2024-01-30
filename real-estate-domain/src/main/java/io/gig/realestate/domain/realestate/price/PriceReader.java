package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.realestate.price.dto.PriceListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
public interface PriceReader {
    List<PriceListDto> getPriceInfoByRealEstateId(Long realEstateId);

    PriceInfo getPriceInfoByPriceId(Long priceId);
}
