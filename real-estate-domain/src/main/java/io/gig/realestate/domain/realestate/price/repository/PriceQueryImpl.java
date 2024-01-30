package io.gig.realestate.domain.realestate.price.repository;

import io.gig.realestate.domain.realestate.price.PriceInfo;
import io.gig.realestate.domain.realestate.price.PriceReader;
import io.gig.realestate.domain.realestate.price.dto.PriceListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PriceQueryImpl implements PriceReader {

    private final PriceQueryRepository queryRepository;

    @Override
    public List<PriceListDto> getPriceInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getPriceInfoByRealEstateId(realEstateId);
    }

    @Override
    public PriceInfo getPriceInfoByPriceId(Long priceId) {
        return queryRepository.getPriceInfoById(priceId);
    }
}
