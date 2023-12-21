package io.gig.realestate.domain.coordinate;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : JAKE
 * @date : 2023/12/20
 */
public interface CoordinateService {
    void readJsonFile(MultipartFile file);
}
