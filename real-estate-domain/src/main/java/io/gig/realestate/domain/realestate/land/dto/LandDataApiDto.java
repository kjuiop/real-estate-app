package io.gig.realestate.domain.realestate.land.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;

/**
 * @author : JAKE
 * @date : 2023/09/25
 */
@Getter
@SuperBuilder
public class LandDataApiDto {

    // 데이터 검증 필요

    /** 토지 필지 번호 **/
    // 고유번호
    // 각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호
    private Long pnu;

    /** 토지면적 **/
    // 각 필지의 지적공부에 등록한 필지의 수평면상 넓이의 합계(㎡)
    private Double lndpclAr;


    /** 지목 **/
    // imreal 에서는 용도지역을 지목으로 사용함
    // 토지의 주된 용도에 따라 토지의 종류를 구분한 지목코드의 코드정보
    private String lndcgrCodeNm;

    /** 용도지역 **/
    // imreal 에서는 지목을 용도지역으로 사용함
    // 도시계획구역 안에서 토지의 효율적인 이용과 공공복리 및 도시기능의 증진을 도모하기 위하여 용도가 지정된 지역을 표시하는 코드정보
    private String prposArea1Nm;

    /** 토지이용상황 **/
    // 토지의 실제이용상황 및 주위의 주된 토지이용상황을 표시하는 코드정보
    private String ladUseSittnNm;

    /** 지형높이 **/
    // 토지에 대한 높이(고저)를 표시하는 코드정보
    private String tpgrphHgCodeNm;

    /** 지형형상 **/
    // 토지형태(모양새)를 표시하는 코드정보
    private String tpgrphFrmCodeNm;

    /** 도로접면 **/
    // 법률에는 도로접면으로 표시되며 필지에 대하여 인접한 도로와의 관계를 구분하는 코드정보
    private String roadSideCodeNm;

    /** 공시지가 **/
    // 대한민국의 건설교통부가 토지의 가격을 조사, 감정을 해 공시함. 개별토지에한 공시 가격(원/㎡)
    private Integer pblntfPclnd;

    /** 공시지가 년도 **/
    // 공시 기준년도
    private Integer stdrYear;



    private String adstrdEmdCode;
    private String prposArea2;
    private String prposArea1;
    private String ldEmdLiCode;
    private String lnmLndcgrSmbol;
    private String trgrphHgCodeNm;
    private String issuConfmCode;
    private String srcObjectId;
    private String slno;
    private String lndcgrCode;
    private String ladUseSittn;
    private String tpgrphFrmCode;
    private String frstRegistDt;
    private String regstrSeCode;
    private String stdrMt;
    private String mnnm;
    private String srcLdCpsgCode;
    private String ldCpsgCode;
    private String prposArea2Nm;

    public static LandDataApiDto convertData(JSONObject nsdi) {
        return LandDataApiDto.builder()
                .pnu(nsdi.getLong("NSDI:PNU"))
                .lndpclAr(nsdi.getDouble("NSDI:LNDPCL_AR"))
                .lndcgrCodeNm(nsdi.getString("NSDI:LNDCGR_CODE_NM"))
                .prposArea1Nm(nsdi.getString("NSDI:PRPOS_AREA_1_NM"))
                .ladUseSittnNm(nsdi.getString("NSDI:LAD_USE_SITTN_NM"))
                .tpgrphFrmCodeNm(nsdi.getString("NSDI:TPGRPH_HG_CODE_NM"))
                .tpgrphFrmCodeNm(nsdi.getString("NSDI:TPGRPH_FRM_CODE_NM"))
                .roadSideCodeNm(nsdi.getString("NSDI:ROAD_SIDE_CODE_NM"))
                .pblntfPclnd(nsdi.getInt("NSDI:PBLNTF_PCLND"))
                .stdrYear(nsdi.getInt("NSDI:STDR_YEAR"))
                .build();
    }
}
