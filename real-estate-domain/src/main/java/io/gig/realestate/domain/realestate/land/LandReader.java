package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.realestate.land.dto.LandDto;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
public interface LandReader {
    List<LandListDto> getLandInfoByRealEstateId(Long realEstateId);
}
