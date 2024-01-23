package io.gig.realestate.domain.realestate.landusage.repository;

import io.gig.realestate.domain.realestate.landusage.LandUsageReader;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/01/23
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LandUsageQueryImpl implements LandUsageReader {

    private final LandUsageQueryRepository queryRepository;

    @Override
    public LandUsageDto getLandUsageInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getLandUsageInfoByRealEstateId(realEstateId);
    }
}
