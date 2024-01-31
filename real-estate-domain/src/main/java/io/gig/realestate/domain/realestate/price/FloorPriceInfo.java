package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.ConstructFloorDataApiDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceUpdateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/07
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FloorPriceInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int flrNo;

    private String flrNoNm;

    private String roomName;

    private Double area;

    private double lndpclAr;

    private double lndpclArByPyung;

    private String mainPurpsCdNm;

    private String etcPurps;

    private String companyName;

    private double guaranteePrice;

    private double rent;

    private double management;

    private String termStartDate;

    private String termEndDate;

    private String etcInfo;

    private int sortOrder;

    private int responseCode;

    private LocalDateTime lastCurlApiAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static FloorPriceInfo create(FloorCreateForm dto, RealEstate realEstate, int sortOrder, Administrator loginUser) {
        return FloorPriceInfo.builder()
                .id(dto.getFloorId())
                .flrNo(dto.getFlrNo())
                .flrNoNm(dto.getFlrNoNm())
                .roomName(dto.getRoomName())
                .area(dto.getArea())
                .lndpclAr(dto.getLndpclAr())
                .lndpclArByPyung(dto.getLndpclArByPyung())
                .mainPurpsCdNm(dto.getMainPurpsCdNm())
                .etcPurps(dto.getEtcPurps())
                .companyName(dto.getCompanyName())
                .guaranteePrice(dto.getGuaranteePrice())
                .rent(dto.getRent())
                .management(dto.getManagement())
                .termStartDate(dto.getTermStartDate())
                .termEndDate(dto.getTermEndDate())
                .etcInfo(dto.getEtcInfo())
                .sortOrder(sortOrder)
                .responseCode(dto.getResponseCode())
                .lastCurlApiAt(dto.getLastCurlApiAt())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .realEstate(realEstate)
                .build();
    }

    public void update(FloorCreateForm dto, int sortOrder, Administrator loginUser) {
        this.flrNo = dto.getFlrNo();
        this.flrNoNm = dto.getFlrNoNm();
        this.area = dto.getArea();
        this.mainPurpsCdNm = dto.getMainPurpsCdNm();
        this.etcPurps = dto.getEtcPurps();
        this.companyName = dto.getCompanyName();
        this.guaranteePrice = dto.getGuaranteePrice();
        this.rent = dto.getRent();
        this.management = dto.getManagement();
        this.responseCode = dto.getResponseCode();
        this.lastCurlApiAt = dto.getLastCurlApiAt();
        this.sortOrder = sortOrder;
        this.updatedBy = loginUser;
    }

    public static FloorPriceInfo createByExcelUpload(ConstructFloorDataApiDto dto, RealEstate realEstate) {
        return FloorPriceInfo.builder()
                .flrNo(dto.getFlrNo())
                .flrNoNm(dto.getFlrNoNm())
                .area(dto.getArea())
                .mainPurpsCdNm(dto.getMainPurpsCdNm())
                .etcPurps(dto.getEtcPurps())
                .realEstate(realEstate)
                .build();
    }
}
