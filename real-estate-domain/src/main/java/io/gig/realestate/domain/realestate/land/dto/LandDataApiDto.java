package io.gig.realestate.domain.realestate.land.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    private String pnuStr;

    /** 토지면적 **/
    // 각 필지의 지적공부에 등록한 필지의 수평면상 넓이의 합계(㎡)
    private BigDecimal lndpclAr;

    /** 토지면적 **/
    // 각 필지의 지적공부에 등록한 필지의 수평면상 넓이의 합계(㎡)
    private BigDecimal lndpclArByPyung;


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

    /** 토지면적당 공시지가 **/
    // 대한민국의 건설교통부가 토지의 가격을 조사, 감정을 해 공시함. 개별토지에한 공시 가격(원/㎡)
    private Integer pblntfPclnd;

    /** 토지면적당 공시지가 합계 **/
    // 대한민국의 건설교통부가 토지의 가격을 조사, 감정을 해 공시함. 개별토지에한 공시 가격(원/㎡)
    private double totalPblntfPclnd;

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
        int pblntfPclnd = nsdi.getInt("NSDI:PBLNTF_PCLND");
        Double lndpclAr = nsdi.getDouble("NSDI:LNDPCL_AR");
        double calculate = pblntfPclnd * lndpclAr;


        double areaInPyung = lndpclAr / 3.305785;
        BigDecimal lndpclArByPyung = new BigDecimal(areaInPyung).setScale(2, RoundingMode.HALF_UP);

        long pnu = nsdi.getLong("NSDI:PNU");
        String pnuStr = String.valueOf(pnu);

        return LandDataApiDto.builder()
                .pnu(pnu)
                .pnuStr(pnuStr)
                .lndpclAr(nsdi.has("NSDI:LNDPCL_AR") ? nsdi.getBigDecimal("NSDI:LNDPCL_AR") : null)
                .lndpclArByPyung(lndpclArByPyung)
                .lndcgrCodeNm(nsdi.has("NSDI:LNDCGR_CODE_NM") ? nsdi.getString("NSDI:LNDCGR_CODE_NM") : null)
                .prposArea1Nm(nsdi.has("NSDI:PRPOS_AREA_1_NM") ? nsdi.getString("NSDI:PRPOS_AREA_1_NM") : null)
                .ladUseSittnNm(nsdi.has("NSDI:LAD_USE_SITTN_NM") ? nsdi.getString("NSDI:LAD_USE_SITTN_NM") : null)
                .tpgrphHgCodeNm(nsdi.has("NSDI:TPGRPH_HG_CODE_NM") ? nsdi.getString("NSDI:TPGRPH_HG_CODE_NM") : null)
                .tpgrphFrmCodeNm(nsdi.has("NSDI:TPGRPH_FRM_CODE_NM") ? nsdi.getString("NSDI:TPGRPH_FRM_CODE_NM") : null)
                .roadSideCodeNm(nsdi.has("NSDI:ROAD_SIDE_CODE_NM") ? nsdi.getString("NSDI:ROAD_SIDE_CODE_NM") : null)
                .pblntfPclnd(nsdi.has("NSDI:PBLNTF_PCLND") ? nsdi.getInt("NSDI:PBLNTF_PCLND") : null)
                .totalPblntfPclnd(calculate)
                .stdrYear(nsdi.has("NSDI:STDR_YEAR") ? nsdi.getInt("NSDI:STDR_YEAR") : null)
                .build();
    }

    @Getter
    @Builder
    public static class Request {
        private final String pnu;

        public static Request assembleParam(String bCode, String landType, String bun, String ji) {

            String landCode = "1";
            if (landType.equals("mountain")) {
                landCode = "2";
            }

            if (!StringUtils.hasText(ji)) {
                ji = "0";
            }
            String bunCode = String.format("%04d", Integer.parseInt(bun));
            String jiCode = String.format("%04d", Integer.parseInt(ji));

            StringBuilder pnuBuilder = new StringBuilder();
            pnuBuilder.append(bCode);
            pnuBuilder.append(landCode);
            pnuBuilder.append(bunCode);
            pnuBuilder.append(jiCode);

            return Request.builder()
                    .pnu(pnuBuilder.toString())
                    .build();
        }
    }

}
