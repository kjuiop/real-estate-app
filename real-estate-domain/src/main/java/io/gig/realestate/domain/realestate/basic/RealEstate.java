package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateUpdateForm;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.construct.ConstructInfo;
import io.gig.realestate.domain.realestate.curltraffic.CurlTrafficLight;
import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.image.ImageInfo;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.landprice.LandPriceInfo;
import io.gig.realestate.domain.realestate.landusage.LandUsageInfo;
import io.gig.realestate.domain.realestate.manager.RealEstateManager;
import io.gig.realestate.domain.realestate.memo.MemoInfo;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.PriceInfo;
import io.gig.realestate.domain.realestate.print.PrintInfo;
import io.gig.realestate.domain.realestate.vertex.Vertex;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private String surroundInfo;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private String addressDetail;

    private String imgUrl;

    private String characterInfo;

    private String agentName;

    private String tradingAt;

    private String acquiredAt;

    @Lob
    private String exclusiveCds;

    @Lob
    private String realEstateGradeCds;

    @Lob
    private String buildingTypeCds;

    @Lob
    private String usageCds;

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
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType banAdvertisingYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProcessType processType = ProcessType.Working;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_type_id")
    private Category usageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_type_id")
    private Category propertyType;

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<LandInfo> landInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<LandUsageInfo> landUsageInfoList = new ArrayList<>();

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

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Vertex> vertexInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<LandPriceInfo> landPriceInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CurlTrafficLight> curlTrafficInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RealEstateManager> managers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_by_id")
    private Administrator managerBy;

    public void addLandInfo(LandInfo landInfo) {
        this.landInfoList.add(landInfo);
    }

    public void addLandUsageInfo(LandUsageInfo landUsageInfo) {
        this.landUsageInfoList.add(landUsageInfo);
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

    public void addLandPriceInfo(LandPriceInfo landPriceInfo) {
        this.landPriceInfoList.add(landPriceInfo);
    }

    public void addCurlTrafficInfo(CurlTrafficLight curlTrafficLight) {
        this.curlTrafficInfoList.add(curlTrafficLight);
    }

    public static RealEstate create(RealEstateCreateForm createForm, Administrator manager, Administrator createdBy) {
        return RealEstate.builder()
                .buildingTypeCds(createForm.getBuildingTypeCds())
                .realEstateGradeCds(createForm.getRealEstateGradeCds())
                .usageCds(createForm.getUsageCds())
                .exclusiveCds(createForm.getExclusiveCds())
                .buildingName(createForm.getBuildingName())
                .surroundInfo(createForm.getSurroundInfo())
                .imgUrl(createForm.getImgUrl())
                .legalCode(createForm.getLegalCode())
                .landType(createForm.getLandType())
                .bun(createForm.getBun())
                .ji(createForm.getJi())
                .address(createForm.getAddress())
                .addressDetail(createForm.getAddressDetail())
                .characterInfo(createForm.getCharacterInfo())
                .agentName(createForm.getAgentName())
                .banAdvertisingYn(createForm.getBanAdvertisingYn())
                .managerBy(manager)
                .acquiredAt(createForm.getAcquiredAt())
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }

    public static RealEstate createByExcelUpload(String agentName, String address, String legalCode, String bun, String ji, ProcessType processType, String characterInfo, Category usageType, Administrator loginUser) {
        return RealEstate.builder()
                .agentName(agentName)
                .address(address)
                .legalCode(legalCode)
                .landType("general")
                .bun(bun)
                .ji(ji)
                .processType(processType)
                .characterInfo(characterInfo)
                .usageType(usageType)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .managerBy(loginUser)
                .build();
    }

    public void update(RealEstateUpdateForm updateForm, Administrator manager, Administrator loginUser) {
        this.exclusiveCds = updateForm.getExclusiveCds();
        this.buildingName = updateForm.getBuildingName();
        this.agentName = updateForm.getAgentName();
        this.surroundInfo = updateForm.getSurroundInfo();
        this.addressDetail = updateForm.getAddressDetail();
        this.characterInfo = updateForm.getCharacterInfo();
        this.acquiredAt = updateForm.getAcquiredAt();
        this.imgUrl = updateForm.getImgUrl();
        this.banAdvertisingYn = updateForm.getBanAdvertisingYn();
        this.managerBy = manager;
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

    public void setUsageType(Category usageType) {
        this.usageType = usageType;
    }

    public void setPropertyType(Category propertyType) {
        this.propertyType = propertyType;
    }

    public void updateRStatus(YnType rYn) {
        this.rYn = rYn;
    }

    public void updateABStatus(YnType abYn) {
        this.abYn = abYn;
    }

    public void updateImageFullPath(String imageUrl) {
        this.imgUrl = imageUrl;
    }

    public void addManager(RealEstateManager realEstateManager) {
        this.managers.add(realEstateManager);
    }
}
