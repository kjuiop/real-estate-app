package io.gig.realestate.domain.realestate.image;

import io.gig.realestate.domain.realestate.image.dto.ImageDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
public interface ImageService {
    List<ImageDto> getSubImageInfoByRealEstateId(Long realEstateId);
}
