package io.gig.realestate.domain.realestate.construct.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2023/10/14
 */
@Getter
@Builder
public class FloorCreateForm {

    private Long floorId;

    private int flrNo;

    private String flrNoNm;

    private String roomName;

    // 면적
    private Double area;

    private double lndpclAr;

    private double lndpclArByPyung;

    // 주용도
    private String mainPurpsCdNm;

    // 부용도
    private String etcPurps;

    private String companyName;

    private int guaranteePrice;

    private int rent;

    private int management;

    private String termStartDate;

    private String termEndDate;

    private String etcInfo;
}
