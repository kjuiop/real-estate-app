package io.gig.realestate.domain.realestate.curltraffic.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2024/01/19
 */
@Getter
@RequiredArgsConstructor
public enum TrafficType {

    Success("success", "성공"),
    RetryNeed("retryNeed", "재시도필요"),
    Fail("fail", "실패"),
    NotYet("notYet", "대기");

    final private String type;
    final private String description;

}
