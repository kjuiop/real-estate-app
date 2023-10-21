package io.gig.realestate.domain.realestate.image.dto;

import io.gig.realestate.domain.realestate.image.ImageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class ImageDto {

    private Long imageId;

    private String fullPath;

    public ImageDto(ImageInfo i) {
        this.imageId = i.getId();
        this.fullPath = i.getFullPath();
    }
}
