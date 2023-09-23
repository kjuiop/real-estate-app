package io.gig.realestate.domain.realestate.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Getter
public class RealEstateCreateForm {

    private String managerUsername;

    public YnType ownYn;

    public String bCode;

    public String hCode;

    private String buildingName;

    private String etcInfo;

    private Long usageTypeId;

    private String address;

    private String addressDetail;

}
