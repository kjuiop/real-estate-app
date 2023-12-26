package io.gig.realestate.domain.realestate.landprice.repository;

import io.gig.realestate.domain.realestate.landprice.LandPriceReader;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/25
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LandPriceQueryImpl implements LandPriceReader {

    private final LandPriceQueryRepository queryRepository;

    @Override
    public List<LandPriceListDto> getLandPriceInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getLandPriceInfoByRealEstateId(realEstateId);
    }
}
