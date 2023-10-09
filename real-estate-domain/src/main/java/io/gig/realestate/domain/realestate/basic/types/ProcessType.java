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

    Preparing("preparing", "준비중", 0),
    Prepare("prepare", "준비", 1),
    Working("working", "작업중", 2),
    Complete("complete", "완료", 3),
    Impossible("impossible", "작업불가", 4),
    Pending("pending", "보류", 4),
    Sell("sell", "매각", 4),
    R("r", "R", 2),
    AB("a-b", "A-B", 2);

    final private String type;
    final private String description;
    final private int level;
}
