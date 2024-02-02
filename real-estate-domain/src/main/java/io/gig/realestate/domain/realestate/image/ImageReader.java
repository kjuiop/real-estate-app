package io.gig.realestate.domain.realestate.image;

import io.gig.realestate.domain.realestate.image.dto.ImageDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
public interface ImageReader {
    List<ImageDto> getSubImageInfoByRealEstateId(Long realEstateId);

    ImageInfo getImageById(Long imageId);
}
