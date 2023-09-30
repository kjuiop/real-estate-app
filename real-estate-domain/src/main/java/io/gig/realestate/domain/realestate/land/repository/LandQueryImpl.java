package io.gig.realestate.domain.realestate.land.repository;

import io.gig.realestate.domain.realestate.land.LandReader;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
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
public class LandQueryImpl implements LandReader {

    private final LandQueryRepository queryRepository;

    @Override
    public List<LandListDto> getLandInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getLandInfoByRealEstateId(realEstateId);
    }
}
