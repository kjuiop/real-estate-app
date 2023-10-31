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
        return ConstructInfo.builder()
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
                .realEstate(realEstate)
                .build();
    }
}
