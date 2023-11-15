package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.ConstructCreateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bldNm;

    private int hhldCnt;

    private int useAprDay;

    private Double platArea;

    private Double platAreaByPyung;

    private Double archArea;

    private Double archAreaByPyung;

    private Double totArea;

    private Double totAreaByPyung;

    private Double bcRat;

    private Double vlRat;

    private Double heit;

    private Double vlRatEstmTotArea;

    private Double vlRatEstmTotAreaByPyung;

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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType illegalConstructYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static ConstructInfo create(ConstructCreateForm createForm, RealEstate realEstate) {
        ConstructInfo constructInfo = ConstructInfo.builder()
                .bldNm(createForm.getBldNm())
                .hhldCnt(createForm.getHhldCnt())
                .useAprDay(createForm.getUseAprDay())
                .platArea(createForm.getPlatArea())
                .platAreaByPyung(createForm.getPlatAreaByPyung())
                .archArea(createForm.getArchArea())
                .bcRat(createForm.getBcRat())
                .totArea(createForm.getTotArea())
                .vlRat(createForm.getVlRat())
                .heit(createForm.getHeit())
                .grndFlrCnt(createForm.getGrndFlrCnt())
                .ugrndFlrCnt(createForm.getUgrndFlrCnt())
                .rideUseElvtCnt(createForm.getRideUseElvtCnt())
                .emgenUseElvtCnt(createForm.getEmgenUseElvtCnt())
                .indrAutoUtcnt(createForm.getIndrAutoUtcnt())
                .oudrAutoUtcnt(createForm.getOudrAutoUtcnt())
                .indrMechUtcnt(createForm.getIndrMechUtcnt())
                .oudrMechUtcnt(createForm.getOudrMechUtcnt())
                .mainPurpsCdNm(createForm.getMainPurpsCdNm())
                .etcPurps(createForm.getEtcPurps())
                .strctCdNm(createForm.getStrctCdNm())
                .illegalConstructYn(createForm.getIllegalConstructYn())
                .vlRatEstmTotArea(createForm.getVlRatEstmTotArea())
                .vlRatEstmTotAreaByPyung(createForm.getVlRatEstmTotAreaByPyung())
                .realEstate(realEstate)
                .build();

        constructInfo.totAreaByPyung = calculatePyung(constructInfo.getTotArea());
        constructInfo.archAreaByPyung = calculatePyung(constructInfo.getArchArea());

        return constructInfo;
    }

    private static Double calculatePyung(Double area) {
        if (area == null || area == 0) {
            return 0.0;
        }

        double result = area / 3.305785;
        result = Math.round(result * 100.0) / 100.0;
        return result;
    }
}
