package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Getter
@Setter
public class RealEstateCreateForm {

    private String managerUsername;

    private YnType ownYn;

    public String bCode;

    public String hCode;

    private String buildingName;

    private String etcInfo;

    private Long usageTypeId;

    private String address;

    private String addressDetail;

}
