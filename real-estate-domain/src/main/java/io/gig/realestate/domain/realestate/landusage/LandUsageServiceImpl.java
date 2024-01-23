package io.gig.realestate.domain.realestate.landusage;

import io.gig.realestate.domain.realestate.landusage.dto.LandUsageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/01/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LandUsageServiceImpl implements LandUsageService {

    private final LandUsageReader landUsageReader;

    @Override
    @Transactional(readOnly = true)
    public LandUsageDto getLandUsageInfo(Long realEstateId) {
        return landUsageReader.getLandUsageInfoByRealEstateId(realEstateId);
    }
}
