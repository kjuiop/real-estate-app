package io.gig.realestate.domain.realestate.landprice;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceCreateForm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/12/25
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LandPriceInfo extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnu;

    private int pclndStdrYear;

    private int pblntfPclnd;

    private int pblntfPclndPy;

    private double changeRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static LandPriceInfo create(LandPriceCreateForm dto, RealEstate realEstate, Administrator createdBy) {
        return LandPriceInfo.builder()
                .pnu(dto.getPnu())
                .pclndStdrYear(dto.getPclndStdrYear())
                .pblntfPclnd(dto.getPblntfPclnd())
                .pblntfPclndPy(dto.getPblntfPclndPy())
                .changeRate(dto.getChangeRate())
                .realEstate(realEstate)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }
}
