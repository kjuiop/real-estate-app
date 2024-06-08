package io.gig.realestate.domain.buyer.basic.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@RequiredArgsConstructor
public enum CompleteType {

    Proceeding("proceeding", "진행중"),
    Complete("complete", "완료"),
    Stop("stop", "중단");

    final private String type;
    final private String description;
}
