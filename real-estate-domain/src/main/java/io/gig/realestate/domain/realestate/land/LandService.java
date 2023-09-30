package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.land.dto.LandCreateForm;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;

import java.io.IOException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
public interface LandService {
    List<LandDataApiDto> getLandListInfo(String bCode, String landType, String bun, String ji) throws IOException;

    Long create(LandCreateForm createForm, LoginUser loginUser);
}
