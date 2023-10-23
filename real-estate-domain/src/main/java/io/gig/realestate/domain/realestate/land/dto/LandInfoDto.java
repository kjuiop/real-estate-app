package io.gig.realestate.domain.realestate.land.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2023/10/13
 */
@Getter
@Builder
public class LandInfoDto {

    private String address;

    private String pnu;

    private String lndcgrCodeNm;

    private String lndpclAr;

    private String lndpclArByPyung;

    private String pblntfPclnd;

    private String totalPblntfPclnd;

    private String prposArea1Nm;

    private String roadSideCodeNm;

    private String tpgrphFrmCodeNm;

    private String tpgrphHgCodeNm;

    private String ladUseSittnNm;

    private YnType commercialYn;
}
