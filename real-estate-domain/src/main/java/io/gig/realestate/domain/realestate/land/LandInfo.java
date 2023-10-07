package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.land.dto.LandCreateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LandInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnu;

    private String address;

    private String lndcgrCodeNm;

    private Double lndpclAr;

    private Double lndpclArByPyung;

    private Double pblntfPclnd;

    private Double totalPblntfPclnd;

    private String prposArealNm;

    private String roadSideCodeNm;

    private String tpgrphFrmCodeNm;

    private String tpgrphHgCodeNm;

    private String ladUseSittnNm;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static LandInfo create(LandCreateForm createForm, RealEstate realEstate) {
        return LandInfo.builder()
                .address(createForm.getAddress())
                .lndcgrCodeNm(createForm.getLndcgrCodeNm())
                .lndpclAr(Double.parseDouble(createForm.getLndpclAr()))
                .lndpclArByPyung(Double.parseDouble(createForm.getLndpclArByPyung()))
                .pblntfPclnd(Double.parseDouble(createForm.getPblntfPclnd()))
                .totalPblntfPclnd(Double.parseDouble(createForm.getTotalPblntfPclnd()))
                .prposArealNm(createForm.getPrposArealNm())
                .roadSideCodeNm(createForm.getRoadSideCodeNm())
                .tpgrphFrmCodeNm(createForm.getTpgrphFrmCodeNm())
                .tpgrphHgCodeNm(createForm.getTpgrphHgCodeNm())
                .ladUseSittnNm(createForm.getLadUseSittnNm())
                .realEstate(realEstate)
                .build();
    }
}
