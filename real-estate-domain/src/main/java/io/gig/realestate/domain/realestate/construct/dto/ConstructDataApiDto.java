package io.gig.realestate.domain.realestate.construct.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
@Getter
@Builder
public class ConstructDataApiDto {

    // 건물명
    private String bldNm;

    // 세대수
    private int hhldCnt;

    private String houseHoldName;

    // 사용승인일
    private int useAprDay;

    private String useAprDate;

    // 대지면적
    private double platArea;

    // 대지면적 평
    private double platAreaByPyung;

    // 건물면적
    private Double archArea;

    private double archAreaByPyung;

    // 건폐율
    private Double bcRat;

    // 연면적
    private Double totArea;

    private double totAreaByPyung;

    // 용적율
    private Double vlRat;

    // 층수
    // 높이
    private Double heit;

    // 지상층 면적
    private Double vlRatEstmTotArea;

    private Double vlRatEstmTotAreaByPyung;

    // 지상층수
    private int grndFlrCnt;

    // 지하층수
    private int ugrndFlrCnt;

    // 엘리베이터

    // 승용 승강기수
    private int rideUseElvtCnt;

    // 비상용 승강기수
    private int emgenUseElvtCnt;

    // 옥내 자주식 주차 대수
    private int indrAutoUtcnt;

    // 옥외 자주식 주차 대수
    private int oudrAutoUtcnt;

    // 옥내 기계식 주차 대수
    private int indrMechUtcnt;

    // 옥외 기계식 주차 대수
    private int oudrMechUtcnt;

    // 주용도
    private String mainPurpsCdNm;

    // 부용도
    private String etcPurps;

    // 구조
    private String strctCdNm;

    public static ConstructDataApiDto convertData(JSONObject item) {

        double platArea = item.has("platArea") ? item.optDouble("platArea") : 0;
        double platAreaByPyung = calculatePyung(platArea);

        Double bcRat = item.has("bcRat") ? item.optDouble("bcRat") : null;
        Double archArea = item.has("archArea") ? item.optDouble("archArea") : null;
        if (bcRat == null && archArea != null && platArea > 0) {
            bcRat = (archArea / platArea) * 100;
            bcRat = Math.round(bcRat * 100.0) / 100.0;
        }

        double archAreaByPyung = calculatePyung(archArea);

        Double vlRat = item.has("vlRat") ? item.optDouble("vlRat") : null;
        Double totArea = item.has("totArea") ? item.optDouble("totArea") : null;
        if (vlRat == null && totArea != null && platArea > 0) {
            vlRat = (totArea / platArea) * 100;
            vlRat = Math.round(vlRat * 100.0) / 100.0;
        }
        double totAreaByPyung = calculatePyung(totArea);

        Double vlRatEstmTotArea = item.has("vlRatEstmTotArea") ? item.optDouble("vlRatEstmTotArea") : 0;
        Double vlRatEstmTotAreaByPyung = calculatePyung(vlRatEstmTotArea);

        int useAprDay = item.has("useAprDay") ? item.optInt("useAprDay") : 0;
        String useAprDate = convertUseAprDay(useAprDay);

        int hhldCnt = item.has("hhldCnt") ? item.optInt("hhldCnt") : 0;
        String houseHoldName = null;
        if (hhldCnt > 0) {
            houseHoldName = String.valueOf(hhldCnt);
        }



        return ConstructDataApiDto.builder()
                .bldNm(item.has("bldNm") ? item.optString("bldNm") : null)
                .houseHoldName(houseHoldName)
                .useAprDay(useAprDay)
                .useAprDate(useAprDate)
                .platArea(item.has("platArea") ? item.optDouble("platArea") : 0)
                .platAreaByPyung(platAreaByPyung)
                .archArea(archArea)
                .archAreaByPyung(archAreaByPyung)
                .bcRat(bcRat)
                .totArea(totArea)
                .totAreaByPyung(totAreaByPyung)
                .vlRat(vlRat)
                .heit(item.has("heit") ? item.getDouble("heit") : null)
                .rideUseElvtCnt(item.has("rideUseElvtCnt") ? item.optInt("rideUseElvtCnt") : 0)
                .emgenUseElvtCnt(item.has("emgenUseElvtCnt") ? item.optInt("emgenUseElvtCnt") : 0)
                .grndFlrCnt(item.has("grndFlrCnt") ? item.optInt("grndFlrCnt") : 0)
                .ugrndFlrCnt(item.has("ugrndFlrCnt") ? item.optInt("ugrndFlrCnt") : 0)
                .indrAutoUtcnt(item.has("indrAutoUtcnt") ? item.optInt("indrAutoUtcnt") : 0)
                .oudrAutoUtcnt(item.has("oudrAutoUtcnt") ? item.optInt("oudrAutoUtcnt") : 0)
                .indrMechUtcnt(item.has("indrMechUtcnt") ? item.optInt("indrMechUtcnt") : 0)
                .oudrMechUtcnt(item.has("oudrMechUtcnt") ? item.optInt("oudrMechUtcnt") : 0)
                .mainPurpsCdNm(item.has("mainPurpsCdNm") ? item.optString("mainPurpsCdNm") : null)
                .etcPurps(item.has("etcPurps") ? item.optString("etcPurps") : null)
                .strctCdNm(item.has("strctCdNm") ? item.optString("strctCdNm") : null)
                .vlRatEstmTotArea(vlRatEstmTotArea)
                .vlRatEstmTotAreaByPyung(vlRatEstmTotAreaByPyung)
                .build();
    }

    private static Double calculatePyung(Double area) {
        if (area == null || area == 0) {
            return 0.0;
        }

        double result = area / 3.305785;
        result = Math.round(result * 100.0) / 100.0;
        return result;
    }

    private static String convertUseAprDay(int useAprDay) {
        if (useAprDay == 0) {
            return null;
        }

        String useAprDayStr = String.valueOf(useAprDay);
        if (!StringUtils.hasText(useAprDayStr) || useAprDayStr.length() != 8) {
            return null;
        }

        int year = Integer.parseInt(useAprDayStr.substring(0, 4));
        int month = Integer.parseInt(useAprDayStr.substring(4, 6));
        int day = Integer.parseInt(useAprDayStr.substring(6));

        LocalDateTime useAprDate = LocalDateTime.of(year, month, day, 0, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return useAprDate.format(formatter);
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
