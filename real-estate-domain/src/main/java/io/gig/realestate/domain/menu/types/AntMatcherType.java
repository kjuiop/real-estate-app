package io.gig.realestate.domain.menu.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@RequiredArgsConstructor
public enum AntMatcherType {

    Single("single", "설정된 값만 사용(/)"),
    All("all", "하위값까지 포함(/**)");

    final private String type;
    final private String description;

}
