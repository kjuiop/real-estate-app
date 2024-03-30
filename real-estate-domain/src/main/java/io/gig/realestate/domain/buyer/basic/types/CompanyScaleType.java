package io.gig.realestate.domain.buyer.basic.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@RequiredArgsConstructor
public enum CompanyScaleType {

    Large("large", "대"),
    Middle("middle", "중"),
    Small("small", "소");

    final private String type;
    final private String description;
}
