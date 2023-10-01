package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.realestate.construct.dto.ConstructDto;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
public interface ConstructReader {
    ConstructDto getConstructInfoByRealEstateId(Long realEstateId);
}
