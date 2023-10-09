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

    private String managerUsername;

    private YnType ownYn;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String buildingName;

    private String etcInfo;

    private Long usageTypeId;

    private String address;

    private String addressDetail;

    private ProcessType processType;
}
