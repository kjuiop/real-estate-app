package io.gig.realestate.domain.realestate.image.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@Setter
@Getter
public class ImageCreateForm {

    private Long imageId;

    private String fullPath;
}
