package io.gig.realestate.domain.realestate.construct.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
@Getter
@Builder
public class ConstructFloorDataApiDto {

    // 층
    private int flrNo;

    private String flrNoNm;

    // 면적
    private Double area;

    // 주용도
    private String mainPurpsCdNm;

    // 부용도
    private String etcPurps;

    private double lndpclAr;

    private double lndpclArByPyung;

    public static ConstructFloorDataApiDto convertData(JSONObject item) {
        return ConstructFloorDataApiDto.builder()
                .flrNo(item.has("flrNo") ? item.optInt("flrNo") : 0)
                .flrNoNm(item.has("flrNoNm") ? item.optString("flrNoNm") : null)
                .area(item.has("area") ? item.optDouble("area") : null)
                .lndpclAr(item.has("area") ? item.optDouble("area") : 0.0)
                .lndpclArByPyung(item.has("area") ? calculatePyung(item.optDouble("area")) : 0.0)
                .mainPurpsCdNm(item.has("mainPurpsCdNm") ? item.optString("mainPurpsCdNm") : null)
                .etcPurps(item.has("etcPurps") ? item.optString("etcPurps") : null)
                .build();
    }

    private static double calculatePyung(Double area) {
        if (area == null || area == 0) {
            return 0.0;
        }

        double result = area / 3.305785;
        result = Math.round(result * 100.0) / 100.0;
        return result;
    }


    @Getter
    @Builder
    public static class Request {
        private final String sigunguCd;
        private final String bjdongCd;
        private final String platGbCd;
        private final String bun;
        private final String ji;

        public static Request assembleParam(String bCode, String landType, String bun, String ji) {

            String sigunguCd = bCode.substring(0, bCode.length() / 2);
            String bjdongCd = bCode.substring(bCode.length() / 2);
            String landCode = "0";
            if (landType.equals("mountain")) {
                landCode = "1";
            } else if (landType.equals("block")) {
                landCode = "2";
            }
            if (!StringUtils.hasText(ji)) {
                ji = "0";
            }
            String bunCode = String.format("%04d", Integer.parseInt(bun));
            String jiCode = String.format("%04d", Integer.parseInt(ji));

            return Request.builder()
                    .sigunguCd(sigunguCd)
                    .bjdongCd(bjdongCd)
                    .platGbCd(landCode)
                    .bun(bunCode)
                    .ji(jiCode)
                    .build();
        }
    }
}
