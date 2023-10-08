package io.gig.realestate.domain.realestate.land.dto;

import lombok.Builder;
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
public class LandCreateForm {

    private Long realEstateId;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private List<LandInfoDto> landInfoList = new ArrayList<>();

    @Getter
    @Builder
    public static class LandInfoDto {

        private String pnu;

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
}
