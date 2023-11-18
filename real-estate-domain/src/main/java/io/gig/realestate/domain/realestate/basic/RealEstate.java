package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateUpdateForm;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.construct.ConstructInfo;
import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.image.ImageInfo;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.memo.MemoInfo;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.PriceInfo;
import io.gig.realestate.domain.realestate.print.PrintInfo;
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

    private String imgUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType ownExclusiveYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType otherExclusiveYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType rYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType abYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProcessType processType = ProcessType.Prepare;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_type_id")
    private Category usageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_by_id")
    private Administrator manager;

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<LandInfo> landInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PriceInfo> priceInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<FloorPriceInfo> floorPriceInfo = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ConstructInfo> constructInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CustomerInfo> customerInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MemoInfo> memoInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ImageInfo> subImgInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PrintInfo> printInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public void addLandInfo(LandInfo landInfo) {
        this.landInfoList.add(landInfo);
    }

    public void addPriceInfo(PriceInfo priceInfo) {
        this.priceInfoList.add(priceInfo);
    }

    public void addConstructInfo(ConstructInfo constructInfo) {
        this.constructInfoList.add(constructInfo);
    }

    public void addCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfoList.add(customerInfo);
    }

    public void addMemoInfo(MemoInfo memoInfo) {
        this.memoInfoList.add(memoInfo);
    }

    public void addFloorInfo(FloorPriceInfo floorPriceInfo) {
        this.floorPriceInfo.add(floorPriceInfo);
    }

    public void addImageInfo(ImageInfo imageInfo) {
        this.subImgInfoList.add(imageInfo);
    }

    public void addPrintInfo(PrintInfo printInfo) {
        this.printInfoList.add(printInfo);
    }

    public static RealEstate create(RealEstateCreateForm createForm, Administrator manager, Administrator createdBy) {
        return RealEstate.builder()
                .buildingName(createForm.getBuildingName())
                .etcInfo(createForm.getEtcInfo())
                .imgUrl(createForm.getImgUrl())
                .legalCode(createForm.getLegalCode())
                .landType(createForm.getLandType())
                .bun(createForm.getBun())
                .ji(createForm.getJi())
                .address(createForm.getAddress())
                .addressDetail(createForm.getAddressDetail())
                .ownExclusiveYn(createForm.getOwnExclusiveYn())
                .otherExclusiveYn(createForm.getOtherExclusiveYn())
                .manager(manager)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }

    public static RealEstate createWithUsageType(RealEstateCreateForm createForm, Administrator manager, Category usageType, Administrator createdBy) {
        return RealEstate.builder()
                .buildingName(createForm.getBuildingName())
                .etcInfo(createForm.getEtcInfo())
                .imgUrl(createForm.getImgUrl())
                .legalCode(createForm.getLegalCode())
                .landType(createForm.getLandType())
                .bun(createForm.getBun())
                .ji(createForm.getJi())
                .address(createForm.getAddress())
                .addressDetail(createForm.getAddressDetail())
                .ownExclusiveYn(createForm.getOwnExclusiveYn())
                .otherExclusiveYn(createForm.getOtherExclusiveYn())
                .usageType(usageType)
                .manager(manager)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }

    public void update(RealEstateUpdateForm updateForm, Administrator manager, Category usageType, Administrator loginUser) {
        this.buildingName = updateForm.getBuildingName();
        this.etcInfo = updateForm.getEtcInfo();
        this.addressDetail = updateForm.getAddressDetail();
        this.ownExclusiveYn = updateForm.getOwnExclusiveYn();
        this.otherExclusiveYn = updateForm.getOtherExclusiveYn();
        this.usageType = usageType;
        this.imgUrl = updateForm.getImgUrl();
        this.manager = manager;
        this.updatedBy = loginUser;
    }

    public static RealEstate initialInfo(String legalCode, String address, String landType, String bun, String ji) {
        return RealEstate.builder()
                .legalCode(legalCode)
                .address(address)
                .landType(landType)
                .bun(bun)
                .ji(ji)
                .build();
    }

    public void updateProcessStatus(ProcessType processType, Administrator loginUser) {
        this.processType = processType;
        this.updatedBy = loginUser;
    }

    public void updateRStatus(YnType rYn) {
        this.rYn = rYn;
    }

    public void updateABStatus(YnType abYn) {
        this.abYn = abYn;
    }
}
