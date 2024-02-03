package io.gig.realestate.domain.realestate.landusage;

import io.gig.realestate.domain.realestate.landusage.dto.LandUsageDto;

/**
 * @author : JAKE
 * @date : 2024/01/23
 */
public interface LandUsageService {
    LandUsageDto getLandUsageInfo(Long realEstateId);

    LandUsageInfo getLandUsageInfoById(Long landUsageId);
}
