package io.gig.realestate.domain.realestate.landprice;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceCreateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private int responseCode;

    private LocalDateTime lastCurlApiAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

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
                .id(dto.getLandPriceId())
                .pnu(dto.getPnu())
                .pclndStdrYear(dto.getPclndStdrYear())
                .pblntfPclnd(dto.getPblntfPclnd())
                .pblntfPclndPy(dto.getPblntfPclndPy())
                .changeRate(dto.getChangeRate())
                .responseCode(dto.getResponseCode())
                .lastCurlApiAt(dto.getLastCurlApiAt())
                .realEstate(realEstate)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }

    public void update(LandPriceCreateForm dto, Administrator loginUser) {
        this.pnu = dto.getPnu();
        this.pclndStdrYear = dto.getPclndStdrYear();
        this.pblntfPclnd = dto.getPblntfPclnd();
        this.pblntfPclndPy = dto.getPblntfPclndPy();
        this.changeRate = dto.getChangeRate();
        this.responseCode = dto.getResponseCode();
        this.lastCurlApiAt = dto.getLastCurlApiAt();
        this.updatedBy = loginUser;
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }
}
