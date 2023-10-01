package io.gig.realestate.domain.realestate.land.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Getter
@Setter
public class LandCreateForm {

    private Long realEstateId;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private String lndcgrCodeNm;

    private String lndpclAr;

    private String lndpclArByPyung;

    private String pblntfPclnd;

    private String totalPblntfPclnd;

    private String prposArealNm;

    private String roadSideCodeNm;

    private String tpgrphFrmCodeNm;

    private String tpgrphHgCodeNm;

    private String ladUseSittnNm;
}
