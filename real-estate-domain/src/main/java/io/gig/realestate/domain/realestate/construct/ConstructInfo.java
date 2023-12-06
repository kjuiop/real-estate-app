package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.ConstructCreateForm;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private String houseHoldName;

    private int useAprDay;

    private LocalDateTime useAprDate;

    private double platArea;

    private double platAreaByPyung;

    private double archArea;

    private double archAreaByPyung;

    private double totArea;

    private double totAreaByPyung;

    private double bcRat;

    private double vlRat;

    private double heit;

    private double vlRatEstmTotArea;

    private double vlRatEstmTotAreaByPyung;

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

        LocalDateTime useAprDate = null;
        if (StringUtils.hasText(createForm.getUseAprDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(createForm.getUseAprDate(), formatter);
            useAprDate = localDate.atStartOfDay();
        }


        ConstructInfo constructInfo = ConstructInfo.builder()
                .bldNm(createForm.getBldNm())
                .hhldCnt(createForm.getHhldCnt())
                .houseHoldName(createForm.getHouseHoldName())
                .useAprDate(useAprDate)
                .platArea(createForm.getPlatArea())
                .platAreaByPyung(createForm.getPlatAreaByPyung())
                .archArea(createForm.getArchArea())
                .archAreaByPyung(createForm.getArchAreaByPyung())
                .bcRat(createForm.getBcRat())
                .totArea(createForm.getTotArea())
                .totAreaByPyung(createForm.getTotAreaByPyung())
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
                .heit(createForm.getHeit())
                .realEstate(realEstate)
                .build();

//        constructInfo.totAreaByPyung = calculatePyung(constructInfo.getTotArea());
//        constructInfo.archAreaByPyung = calculatePyung(constructInfo.getArchArea());

        return constructInfo;
    }

    public static ConstructInfo createByExcelUpload(ConstructDataApiDto constructDto, RealEstate realEstate) {

        LocalDateTime useAprDate = null;
        if (StringUtils.hasText(constructDto.getUseAprDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(constructDto.getUseAprDate(), formatter);
            useAprDate = localDate.atStartOfDay();
        }

        return ConstructInfo.builder()
                .bldNm(constructDto.getBldNm())
                .hhldCnt(constructDto.getHhldCnt())
                .houseHoldName(constructDto.getHouseHoldName())
                .useAprDate(useAprDate)
                .platArea(constructDto.getPlatArea())
                .platAreaByPyung(constructDto.getPlatAreaByPyung())
                .archArea(constructDto.getArchArea())
                .archAreaByPyung(constructDto.getArchAreaByPyung())
                .totArea(constructDto.getTotArea())
                .totAreaByPyung(constructDto.getTotAreaByPyung())
                .bcRat(constructDto.getBcRat())
                .vlRat(constructDto.getVlRat())
                .heit(constructDto.getHeit())
                .vlRatEstmTotArea(constructDto.getVlRatEstmTotArea())
                .vlRatEstmTotAreaByPyung(constructDto.getVlRatEstmTotAreaByPyung())
                .grndFlrCnt(constructDto.getGrndFlrCnt())
                .ugrndFlrCnt(constructDto.getUgrndFlrCnt())
                .rideUseElvtCnt(constructDto.getRideUseElvtCnt())
                .emgenUseElvtCnt(constructDto.getEmgenUseElvtCnt())
                .indrAutoUtcnt(constructDto.getIndrAutoUtcnt())
                .oudrAutoUtcnt(constructDto.getOudrAutoUtcnt())
                .indrMechUtcnt(constructDto.getIndrMechUtcnt())
                .oudrMechUtcnt(constructDto.getOudrMechUtcnt())
                .mainPurpsCdNm(constructDto.getMainPurpsCdNm())
                .etcPurps(constructDto.getEtcPurps())
                .strctCdNm(constructDto.getStrctCdNm())
                .realEstate(realEstate)
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
}
