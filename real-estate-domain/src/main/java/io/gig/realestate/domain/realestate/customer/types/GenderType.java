package io.gig.realestate.domain.realestate.customer.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum GenderType {

    MAN("Man", "남자"),

    WOMAN("Woman", "여자");

    private String key;

    private String description;
}
