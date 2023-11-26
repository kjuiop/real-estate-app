package io.gig.realestate.domain.realestate.construct.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
@Getter
@Setter
public class ConstructCreateForm {

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
    private double archArea;

    // 건폐율
    private double bcRat;

    // 연면적
    private double totArea;

    private double totAreaByPyung;

    // 용적율
    private double vlRat;

    // 층수
    // 높이
    private double heit;

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

    private String companyName;

    private YnType illegalConstructYn;

    private double vlRatEstmTotArea;

    private double vlRatEstmTotAreaByPyung;

}
