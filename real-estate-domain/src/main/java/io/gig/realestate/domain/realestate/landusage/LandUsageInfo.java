package io.gig.realestate.domain.realestate.landusage;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageCreateForm;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageUpdateForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/01/22
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LandUsageInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnu;

    @Lob
    private String posList;

    @Lob
    private String prposAreaDstrcNmList;

    @Lob
    private String prposAreaDstrcCodeList;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

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

    public static LandUsageInfo create(LandUsageCreateForm dto, RealEstate newRealEstate, Administrator loginUser) {
        return LandUsageInfo.builder()
                .pnu(dto.getPnu())
                .prposAreaDstrcCodeList(dto.getPrposAreaDstrcCodeList())
                .prposAreaDstrcNmList(dto.getPrposAreaDstrcNmList())
                .responseCode(dto.getResponseCode())
                .lastCurlApiAt(dto.getLastCurlApiAt())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .realEstate(newRealEstate)
                .build();
    }

    public static LandUsageInfo update(LandUsageUpdateForm dto, RealEstate realEstate, Administrator loginUser) {
        return LandUsageInfo.builder()
                .id(dto.getLandUsageId())
                .pnu(dto.getPnu())
                .prposAreaDstrcCodeList(dto.getPrposAreaDstrcCodeList())
                .prposAreaDstrcNmList(dto.getPrposAreaDstrcNmList())
                .responseCode(dto.getResponseCode())
                .lastCurlApiAt(dto.getLastCurlApiAt())
                .updatedBy(loginUser)
                .realEstate(realEstate)
                .build();
    }
}
