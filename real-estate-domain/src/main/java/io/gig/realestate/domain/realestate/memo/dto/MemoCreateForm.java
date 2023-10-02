package io.gig.realestate.domain.realestate.memo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Getter
@Setter
public class MemoCreateForm {

    private Long realEstateId;

    private String memo;
}
