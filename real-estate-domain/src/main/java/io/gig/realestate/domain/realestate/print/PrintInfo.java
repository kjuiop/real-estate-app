package io.gig.realestate.domain.realestate.print;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateUpdateForm;
import io.gig.realestate.domain.realestate.print.dto.PrintCreateForm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/11/12
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PrintInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "print_id")
    private Long id;

    private String propertyImgUrl;

    private String buildingImgUrl;

    private String locationImgUrl;

    private String landDecreeImgUrl;

    private String developPlanImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static PrintInfo create(PrintCreateForm createForm, RealEstate realEstate) {
        return PrintInfo.builder()
                .realEstate(realEstate)
                .propertyImgUrl(createForm.getPropertyImgUrl())
                .buildingImgUrl(createForm.getBuildingImgUrl())
                .locationImgUrl(createForm.getLocationImgUrl())
                .landDecreeImgUrl(createForm.getLandDecreeImgUrl())
                .developPlanImgUrl(createForm.getDevelopPlanImgUrl())
                .build();
    }
}
