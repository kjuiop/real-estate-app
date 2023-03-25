package io.gig.realestate.domain.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/03/25
 */
@Getter
@Setter
public class BaseSearchDto {
    private int page = 0;
    private int size = 10;
    private String keyword;
}
