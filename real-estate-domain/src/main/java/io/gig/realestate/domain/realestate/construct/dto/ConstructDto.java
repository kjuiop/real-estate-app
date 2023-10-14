package io.gig.realestate.domain.realestate.construct.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.construct.ConstructInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class ConstructDto {

    private Long constructId;

    private String bldNm;

    private int hhldCnt;

    private int useAprDay;

    private double platArea;

    private Double archArea;

    private Double bcRat;

    private Double totArea;

    private Double vlRat;

    private Double heit;

    // 지상
    private int grndFlrCnt;

    // 지하
    private int ugrndFlrCnt;

    // 승용승강기 수
    private int rideUseElvtCnt;

    // 비사용 승강기 수
    private int emgenUseElvtCnt;

    // 옥내 자주식 대수(대)
    private int indrAutoUtcnt;

    // 옥외 자주식 대수
    private int oudrAutoUtcnt;

    // 옥내 기계식 대수
    private int indrMechUtcnt;

    // 옥외 기계식 대수
    private int oudrMechUtcnt;


    private String mainPurpsCdNm;

    private String etcPurps;

    private String strctCdNm;

    private YnType illegalConstructYn;

    public ConstructDto(ConstructInfo c) {
        this.constructId = c.getId();
        this.bldNm = c.getBldNm();
        this.hhldCnt = c.getHhldCnt();
        this.useAprDay = c.getUseAprDay();
        this.platArea = c.getPlatArea();
        this.archArea = c.getArchArea();
        this.bcRat = c.getBcRat();
        this.totArea = c.getTotArea();
        this.vlRat = c.getVlRat();
        this.heit = c.getHeit();
        this.grndFlrCnt = c.getGrndFlrCnt();
        this.ugrndFlrCnt = c.getUgrndFlrCnt();
        this.rideUseElvtCnt = c.getRideUseElvtCnt();
        this.emgenUseElvtCnt = c.getEmgenUseElvtCnt();
        this.indrAutoUtcnt = c.getIndrAutoUtcnt();
        this.oudrAutoUtcnt = c.getOudrAutoUtcnt();
        this.indrMechUtcnt = c.getIndrMechUtcnt();
        this.oudrMechUtcnt = c.getOudrMechUtcnt();
        this.mainPurpsCdNm = c.getMainPurpsCdNm();
        this.etcPurps = c.getEtcPurps();
        this.strctCdNm = c.getStrctCdNm();
        this.illegalConstructYn = c.getIllegalConstructYn();
    }
}
