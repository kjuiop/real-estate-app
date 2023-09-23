package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.realestate.land.dto.LandDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
public interface LandService {
    List<LandDto> getLandListInfoByPnu(String pnu);
}
