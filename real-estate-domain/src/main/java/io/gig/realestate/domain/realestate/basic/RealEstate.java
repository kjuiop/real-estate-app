package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.land.LandInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RealEstate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buildingName;

    private String etcInfo;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private String addressDetail;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType ownYn = YnType.Y;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_type_id")
    private Category processType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_type_id")
    private Category usageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_by_id")
    private Administrator manager;

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<LandInfo> landInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static RealEstate create(RealEstateCreateForm createForm, Administrator manager, Category usageType, Administrator createdBy) {
        return RealEstate.builder()
                .buildingName(createForm.getBuildingName())
                .etcInfo(createForm.getEtcInfo())
                .legalCode(createForm.getLegalCode())
                .landType(createForm.getLandType())
                .bun(createForm.getBun())
                .ji(createForm.getJi())
                .address(createForm.getAddress())
                .addressDetail(createForm.getAddressDetail())
                .ownYn(createForm.getOwnYn())
                .usageType(usageType)
                .manager(manager)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }
}
