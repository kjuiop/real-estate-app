package io.gig.realestate.domain.area;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
public interface AreaService {
    void createByExcelData(MultipartFile file) throws IOException;
}
