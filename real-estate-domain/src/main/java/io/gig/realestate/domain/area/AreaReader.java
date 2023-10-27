package io.gig.realestate.domain.area;

import io.gig.realestate.domain.area.dto.AreaListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
public interface AreaReader {
    List<AreaListDto> getParentAreaList();

    List<AreaListDto> getAreaListByParentId(Long areaId);

    List<AreaListDto> getAreaListBySido(String sido);

    List<AreaListDto> getAreaListByGungu(String gungu);
}
