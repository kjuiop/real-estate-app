package io.gig.realestate.domain.realestate.basic.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@RequiredArgsConstructor
public enum ProcessType {

    Writing("writing", "작성중"),
    Prepare("prepare", "준비"),
    Working("working", "작업중"),
    Complete("complete", "완료"),
    Pending("pending", "보류"),
    SellBefore("sellBefore", "매각전"),
    SellComplete("sellComplete", "매각");

    final private String type;
    final private String description;
}
