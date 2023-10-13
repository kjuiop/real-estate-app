package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/07
 */
@Getter
@Setter
public class RealEstateUpdateForm {

    private Long realEstateId;

    private String buildingName;

    private String etcInfo;

    private String addressDetail;

    private YnType ownExclusiveYn;

    private YnType otherExclusiveYn;

    private Long usageTypeId;

    private String managerUsername;

    private ProcessType processType;
}
