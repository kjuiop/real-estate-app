package io.gig.realestate.domain.realestate.construct.dto;

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

    private int platArea;

    private Double archArea;

    private Double bcRat;

    private Double totArea;

    private Double vlRat;

    private Double heit;

    private int grndFlrCnt;

    private int ugrndFlrCnt;

    private int rideUseElvtCnt;

    private int emgenUseElvtCnt;

    private int indrAutoUtcnt;

    private int oudrAutoUtcnt;

    private int indrMechUtcnt;

    private int oudrMechUtcnt;

    private String mainPurpsCdNm;

    private String etcPurps;

    private String strctCdNm;

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
    }
}
