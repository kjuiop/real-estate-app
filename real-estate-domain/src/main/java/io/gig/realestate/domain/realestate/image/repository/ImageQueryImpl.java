package io.gig.realestate.domain.realestate.image.repository;

import io.gig.realestate.domain.realestate.image.ImageInfo;
import io.gig.realestate.domain.realestate.image.ImageReader;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQueryImpl implements ImageReader {

    private final ImageQueryRepository queryRepository;

    @Override
    public List<ImageDto> getSubImageInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getSubImageInfoByRealEstateId(realEstateId);
    }

    @Override
    public ImageInfo getImageById(Long imageId) {
        return queryRepository.getImageInfoById(imageId);
    }
}
