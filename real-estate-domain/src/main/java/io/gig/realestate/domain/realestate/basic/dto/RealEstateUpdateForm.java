package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    private String imgUrl;

    private String address;

    private String addressDetail;

    private YnType ownExclusiveYn;

    private YnType otherExclusiveYn;

    private Long usageTypeId;

    private String managerUsername;

    private ProcessType processType;

    private List<LandInfoDto> landInfoList = new ArrayList<>();
}
