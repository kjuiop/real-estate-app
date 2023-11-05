package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.FloorCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceUpdateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    private Double area;

    private String mainPurpsCdNm;

    private String etcPurps;

    private String companyName;

    private int guaranteePrice;

    private int rent;

    private int management;

    private String term;

    private String etcInfo;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static FloorPriceInfo create(FloorCreateForm dto, RealEstate realEstate) {
        return FloorPriceInfo.builder()
                .flrNo(dto.getFlrNo())
                .flrNoNm(dto.getFlrNoNm())
                .area(dto.getArea())
                .mainPurpsCdNm(dto.getMainPurpsCdNm())
                .etcPurps(dto.getEtcPurps())
                .companyName(dto.getCompanyName())
                .guaranteePrice(dto.getGuaranteePrice())
                .rent(dto.getRent())
                .management(dto.getManagement())
                .term(dto.getTerm())
                .etcInfo(dto.getEtcInfo())
                .realEstate(realEstate)
                .build();
    }

    public static FloorPriceInfo update(PriceUpdateForm.FloorDto dto, RealEstate realEstate) {
        return FloorPriceInfo.builder()
                .flrNo(dto.getFlrNo())
                .flrNoNm(dto.getFlrNoNm())
                .area(dto.getArea())
                .mainPurpsCdNm(dto.getMainPurpsCdNm())
                .etcPurps(dto.getEtcPurps())
                .companyName(dto.getCompanyName())
                .guaranteePrice(dto.getGuaranteePrice())
                .rent(dto.getRent())
                .management(dto.getManagement())
                .realEstate(realEstate)
                .build();
    }

}
