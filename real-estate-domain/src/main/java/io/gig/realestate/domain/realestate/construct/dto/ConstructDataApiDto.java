package io.gig.realestate.domain.realestate.construct.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

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

    // 사용승인일
    private int useAprDay;

    // 대지면적
    private double platArea;

    // 건물면적
    private Double archArea;

    // 건폐율
    private Double bcRat;

    // 연면적
    private Double totArea;

    // 용적율
    private Double vlRat;

    // 층수
    // 높이
    private Double heit;

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
        return ConstructDataApiDto.builder()
                .bldNm(item.has("bldNm") ? item.getString("bldNm") : null)
                .hhldCnt(item.has("hhldCnt") ? item.getInt("hhldCnt") : 0)
                .useAprDay(item.has("useAprDay") ? item.getInt("useAprDay") : 0)
                .platArea(item.has("platArea") ? item.getInt("platArea") : 0)
                .archArea(item.has("archArea") ? item.getDouble("archArea") : null)
                .bcRat(item.has("bcRat") ? item.getDouble("bcRat") : null)
                .totArea(item.has("totArea") ? item.getDouble("totArea") : null)
                .vlRat(item.has("vlRat") ? item.getDouble("vlRat") : null)
                .heit(item.has("heit") ? item.getDouble("heit") : null)
                .rideUseElvtCnt(item.has("rideUseElvtCnt") ? item.getInt("rideUseElvtCnt") : 0)
                .emgenUseElvtCnt(item.has("emgenUseElvtCnt") ? item.getInt("emgenUseElvtCnt") : 0)
                .grndFlrCnt(item.has("grndFlrCnt") ? item.getInt("grndFlrCnt") : 0)
                .ugrndFlrCnt(item.has("ugrndFlrCnt") ? item.getInt("ugrndFlrCnt") : 0)
                .indrAutoUtcnt(item.has("indrAutoUtcnt") ? item.getInt("indrAutoUtcnt") : 0)
                .oudrAutoUtcnt(item.has("oudrAutoUtcnt") ? item.getInt("oudrAutoUtcnt") : 0)
                .indrMechUtcnt(item.has("indrMechUtcnt") ? item.getInt("indrMechUtcnt") : 0)
                .oudrAutoUtcnt(item.has("oudrAutoUtcnt") ? item.getInt("oudrAutoUtcnt") : 0)
                .mainPurpsCdNm(item.has("mainPurpsCdNm") ? item.getString("mainPurpsCdNm") : null)
                .etcPurps(item.has("etcPurps") ? item.getString("etcPurps") : null)
                .strctCdNm(item.has("strctCdNm") ? item.getString("strctCdNm") : null)
                .build();
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
