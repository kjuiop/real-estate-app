package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
import io.gig.realestate.domain.realestate.land.dto.LandFrlDto;

import java.io.IOException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
public interface LandService {
    List<LandFrlDto> getLandListInfoByPnu(String pnu) throws IOException;
}
