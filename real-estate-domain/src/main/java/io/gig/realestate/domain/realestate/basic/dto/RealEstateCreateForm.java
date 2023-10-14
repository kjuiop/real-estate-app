package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Getter
@Setter
public class RealEstateCreateForm {

    private Long realEstateId;

    private String managerUsername;

    private YnType ownExclusiveYn;

    private YnType otherExclusiveYn;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String buildingName;

    private String etcInfo;

    private Long usageTypeId;

    private String address;

    private String addressDetail;

    private String imgUrl;

    private List<LandInfoDto> landInfoList = new ArrayList<>();

}
