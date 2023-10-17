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

    Prepare("prepare", "준비", 1),
    Working("working", "작업중", 2),
    NotAssign("notAssign", "미지정", 2),
    Complete("complete", "작업완료", 3),
    Impossible("impossible", "작업불가", 5),
    Pending("pending", "매각보류", 4),
    Sell("sell", "매각", 5);

    final private String type;
    final private String description;
    final private int level;
}
