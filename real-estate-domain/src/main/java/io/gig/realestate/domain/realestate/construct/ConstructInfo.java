package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.admin.Administrator;
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

    private int responseCode;

    private LocalDateTime lastCurlApiAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static ConstructInfo create(ConstructCreateForm createForm, RealEstate realEstate, Administrator loginUser) {
        return ConstructInfo.builder()
                .id(createForm.getConstructId())
                .bldNm(createForm.getBldNm())
                .hhldCnt(createForm.getHhldCnt())
                .houseHoldName(createForm.getHouseHoldName())
                .useAprDate(convertUseAprDate(createForm.getUseAprDate()))
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
                .responseCode(createForm.getResponseCode())
                .lastCurlApiAt(createForm.getLastCurlApiAt())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .realEstate(realEstate)
                .build();
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

    public void update(ConstructCreateForm dto, Administrator loginUser) {
        this.bldNm = dto.getBldNm();
        this.hhldCnt = dto.getHhldCnt();
        this.houseHoldName = dto.getHouseHoldName();
        this.useAprDate = convertUseAprDate(dto.getUseAprDate());
        this.platArea = dto.getPlatArea();
        this.platAreaByPyung = dto.getPlatAreaByPyung();
        this.archArea = dto.getArchArea();
        this.archAreaByPyung = dto.getArchAreaByPyung();
        this.bcRat = dto.getBcRat();
        this.totArea = dto.getTotArea();
        this.totAreaByPyung = dto.getTotAreaByPyung();
        this.vlRat = dto.getVlRat();
        this.heit = dto.getHeit();
        this.grndFlrCnt = dto.getGrndFlrCnt();
        this.ugrndFlrCnt = dto.getUgrndFlrCnt();
        this.rideUseElvtCnt = dto.getRideUseElvtCnt();
        this.emgenUseElvtCnt = dto.getEmgenUseElvtCnt();
        this.indrAutoUtcnt = dto.getIndrAutoUtcnt();
        this.oudrAutoUtcnt = dto.getOudrAutoUtcnt();
        this.indrMechUtcnt = dto.getIndrMechUtcnt();
        this.oudrMechUtcnt = dto.getOudrMechUtcnt();
        this.mainPurpsCdNm = dto.getMainPurpsCdNm();
        this.etcPurps = dto.getEtcPurps();
        this.strctCdNm = dto.getStrctCdNm();
        this.illegalConstructYn = dto.getIllegalConstructYn();
        this.vlRatEstmTotArea = dto.getVlRatEstmTotArea();
        this.vlRatEstmTotAreaByPyung = dto.getVlRatEstmTotAreaByPyung();
        this.heit = dto.getHeit();
        this.responseCode = dto.getResponseCode();
        this.lastCurlApiAt = dto.getLastCurlApiAt();
        this.updatedBy = loginUser;
    }

    private static LocalDateTime convertUseAprDate(String useAprDate) {
        if (!StringUtils.hasText(useAprDate)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(useAprDate, formatter);
        return localDate.atStartOfDay();
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }
}
