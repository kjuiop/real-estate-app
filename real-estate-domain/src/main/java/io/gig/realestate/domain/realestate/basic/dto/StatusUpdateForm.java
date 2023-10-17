package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/17
 */
@Getter
@Setter
public class StatusUpdateForm {

    private Long realEstateId;

    private YnType rYn;

    private YnType abYn;
}
