package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.construct.dto.*;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
public interface ConstructService {
    ConstructDataApiDto getConstructInfo(String bCode, String landType, String bun, String ji) throws IOException;

    List<ConstructFloorDataApiDto> getConstructFloorInfo(String bCode, String landType, String bun, String ji) throws IOException;

    Long create(ConstructCreateForm createForm, LoginUser loginUser);

    ConstructDto getConstructInfoByRealEstateId(Long realEstateId);

    List<FloorListDto> getFloorInfoByRealEstateId(Long realEstateId);

    FloorPriceInfo getConstructFloorById(Long floorId);

    ConstructInfo getConstructInfoById(Long constructId);
}
