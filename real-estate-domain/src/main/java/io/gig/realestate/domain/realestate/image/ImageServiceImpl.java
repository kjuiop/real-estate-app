package io.gig.realestate.domain.realestate.image;

import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageReader imageReader;

    @Override
    @Transactional(readOnly = true)
    public List<ImageDto> getSubImageInfoByRealEstateId(Long realEstateId) {
        return imageReader.getSubImageInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional(readOnly = true)
    public ImageInfo getImageInfoById(Long imageId) {
        return imageReader.getImageById(imageId);
    }
}
