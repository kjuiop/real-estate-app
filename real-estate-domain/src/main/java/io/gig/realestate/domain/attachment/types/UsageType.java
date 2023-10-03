package io.gig.realestate.domain.attachment.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Getter
@AllArgsConstructor
public enum UsageType {

    RealEstate("realestate", "매물관리 이미지");

    final private String type;
    final private String description;
}
