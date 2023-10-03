package io.gig.realestate.domain.area;

import io.gig.realestate.domain.area.dto.AreaListDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
public interface AreaService {
    void createByExcelData(MultipartFile file) throws IOException;

    List<AreaListDto> getParentAreaList();

    List<AreaListDto> getAreaListByParentId(Long areaId);
}
