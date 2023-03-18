package io.gig.realestate.domain.common;

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
public enum YnType {

    Y("Y", "Y"),

    N("N", "N");

    private String key;

    private String description;
}
