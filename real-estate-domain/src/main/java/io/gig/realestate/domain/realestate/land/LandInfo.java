package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.land.dto.LandCreateForm;
import io.gig.realestate.domain.team.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class LandInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnu;

    private String lndcgrCodeNm;

    private String lndpclAr;

    private String lndpclArByPyung;

    private String pblntfPclnd;

    private String totalPblntfPclnd;

    private String prposArealNm;

    private String roadSideCodeNm;

    private String tpgrphFrmCodeNm;

    private String tpgrphHgCodeNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static LandInfo create(LandCreateForm createForm, RealEstate realEstate) {
        return LandInfo.builder()
                .lndcgrCodeNm(createForm.getLndcgrCodeNm())
                .lndpclAr(createForm.getLndpclAr())
                .lndpclArByPyung(createForm.getLndpclArByPyung())
                .pblntfPclnd(createForm.getPblntfPclnd())
                .totalPblntfPclnd(createForm.getTotalPblntfPclnd())
                .prposArealNm(createForm.getPrposArealNm())
                .roadSideCodeNm(createForm.getRoadSideCodeNm())
                .tpgrphFrmCodeNm(createForm.getTpgrphFrmCodeNm())
                .tpgrphHgCodeNm(createForm.getTpgrphHgCodeNm())
                .realEstate(realEstate)
                .build();
    }
}
