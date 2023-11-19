package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.land.dto.LandCreateForm;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import io.gig.realestate.domain.realestate.land.dto.LandUpdateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

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

    private double lndpclAr;

    private double lndpclArByPyung;

    private double pblntfPclnd;

    private double pblndfPclndByPyung;

    private double totalPblntfPclnd;

    private double roadWidth;

    private String prposArea1Nm;

    private String roadSideCodeNm;

    private String tpgrphFrmCodeNm;

    private String tpgrphHgCodeNm;

    private String ladUseSittnNm;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType commercialYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static LandInfo create(LandInfoDto dto, RealEstate realEstate) {

        LandInfo landInfo = LandInfo.builder()
                .pnu(dto.getPnu())
                .address(dto.getAddress())
                .address(dto.getAddress())
                .commercialYn(dto.getCommercialYn())
                .lndcgrCodeNm(dto.getLndcgrCodeNm())
                .lndpclAr(StringUtils.hasText(dto.getLndpclAr()) ? Double.parseDouble(dto.getLndpclAr()) : 0)
                .lndpclArByPyung(StringUtils.hasText(dto.getLndpclAr()) ? Double.parseDouble(dto.getLndpclArByPyung()) : 0)
                .pblntfPclnd(StringUtils.hasText(dto.getLndpclAr()) ? Double.parseDouble(dto.getPblntfPclnd()) : 0)
                .totalPblntfPclnd(StringUtils.hasText(dto.getLndpclAr()) ? Double.parseDouble(dto.getTotalPblntfPclnd()) : 0)
                .prposArea1Nm(dto.getPrposArea1Nm())
                .roadSideCodeNm(dto.getRoadSideCodeNm())
                .tpgrphFrmCodeNm(dto.getTpgrphFrmCodeNm())
                .tpgrphHgCodeNm(dto.getTpgrphHgCodeNm())
                .ladUseSittnNm(dto.getLadUseSittnNm())
                .roadWidth(StringUtils.hasText(dto.getRoadWidth()) ? Double.parseDouble(dto.getRoadWidth()) : 0.0)
                .realEstate(realEstate)
                .build();

        if (StringUtils.hasText(dto.getPblntfPclnd())) {
            double pblntfPclnd = Double.parseDouble(dto.getPblntfPclnd());
            landInfo.pblndfPclndByPyung = Math.floor(pblntfPclnd * 3.305785);
        }

        return landInfo;
    }

    public static void update(String address, YnType commercialYn, LandUpdateForm.LandInfoDto dto) {
    }
}
