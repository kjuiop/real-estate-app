package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.land.dto.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
public interface LandService {

    List<LandDataApiDto> getLandListInfo(String bCode, String landType, String bun, String ji) throws IOException;

    List<LandListDto> getLandListInfoByRealEstateId(Long realEstateId);

    LandUsageDataApiDto getLandUsagePublicData(String legalCode, String landType, String bun, String ji) throws IOException;

    LandDataApiDto getLandPublicInfo(String legalCode, String landType, String bun, String ji) throws IOException;

    LandInfo getLandInfoById(Long landId);
}
