package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.BuyerCompleteDto;
import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.buyer.basic.dto.BuyerForm;
import io.gig.realestate.domain.buyer.basic.types.CompleteType;
import io.gig.realestate.domain.buyer.history.BuyerHistory;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.buyer.maps.BuyerHistoryMap;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2024/02/18
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Buyer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CompleteType completeType = CompleteType.Proceeding;

    private int successPercent;

    private String title;

    private String customerName;

    private String customerPhone;

    private String inflowPath;

    private double salePrice;

    private double handCache;

    private double landAreaPy;

    private double totalAreaPy;

    private double exclusiveAreaPy;

    @Lob
    private String buyerGradeCds;

    @Lob
    private String purposeCds;

    @Lob
    private String loanCharacterCds;

    @Lob
    private String preferBuildingCds;

    @Lob
    private String investmentTimingCds;

    private String preferArea;

    private String preferSubway;

    private String preferRoad;

    private String moveYear;

    private String moveMonth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType companyEstablishAtYn = YnType.N;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CompanyScaleType companyScale;

    @Lob
    private String requestDetail;

    @Builder.Default
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<BuyerHistory> histories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<BuyerHistoryMap> maps = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<BuyerManager> managers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_by_id")
    private Administrator managerBy;

    public void addHistory(BuyerHistory history) {
        this.histories.add(history);
    }

    public void addManager(BuyerManager manager) {
        this.managers.add(manager);
    }

    public static Buyer create(BuyerForm createForm, Administrator loginUser) {
        return Buyer.builder()
                .buyerGradeCds(createForm.getBuyerGradeCds())
                .title(createForm.getTitle())
                .successPercent(createForm.getSuccessPercent())
                .customerName(createForm.getCustomerName())
                .customerPhone(createForm.getCustomerName())
                .inflowPath(createForm.getInflowPath())
                .salePrice(createForm.getSalePrice())
                .handCache(createForm.getHandCache())
                .landAreaPy(createForm.getLandAreaPy())
                .totalAreaPy(createForm.getTotalAreaPy())
                .exclusiveAreaPy(createForm.getExclusiveAreaPy())
                .purposeCds(createForm.getPurposeCds())
                .loanCharacterCds(createForm.getLoanCharacterCds())
                .preferBuildingCds(createForm.getPreferBuildingCds())
                .investmentTimingCds(createForm.getInvestmentTimingCds())
                .preferArea(createForm.getPreferArea())
                .preferSubway(createForm.getPreferSubway())
                .preferRoad(createForm.getPreferRoad())
                .moveYear(createForm.getMoveYear())
                .moveMonth(createForm.getMoveMonth())
                .companyEstablishAtYn(createForm.getCompanyEstablishAtYn())
                .companyScale(createForm.getCompanyScale())
                .requestDetail(createForm.getRequestDetail())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .managerBy(loginUser)
                .build();
    }

    public void update(BuyerForm updateForm, LoginUser loginUser) {
        this.buyerGradeCds = updateForm.getBuyerGradeCds();
        this.title = updateForm.getTitle();
        this.successPercent = updateForm.getSuccessPercent();
        this.customerName = updateForm.getCustomerName();
        this.customerPhone = updateForm.getCustomerPhone();
        this.inflowPath = updateForm.getInflowPath();
        this.salePrice = updateForm.getSalePrice();
        this.handCache = updateForm.getHandCache();
        this.landAreaPy = updateForm.getLandAreaPy();
        this.totalAreaPy = updateForm.getTotalAreaPy();
        this.exclusiveAreaPy = updateForm.getExclusiveAreaPy();
        this.purposeCds = updateForm.getPurposeCds();
        this.loanCharacterCds = updateForm.getLoanCharacterCds();
        this.preferBuildingCds = updateForm.getPreferBuildingCds();
        this.investmentTimingCds = updateForm.getInvestmentTimingCds();
        this.preferArea = updateForm.getPreferArea();
        this.preferSubway = updateForm.getPreferSubway();
        this.preferRoad = updateForm.getPreferRoad();
        this.moveYear = updateForm.getMoveYear();
        this.moveMonth = updateForm.getMoveMonth();
        this.companyScale = updateForm.getCompanyScale();
        this.companyEstablishAtYn = updateForm.getCompanyEstablishAtYn();
        this.requestDetail = updateForm.getRequestDetail();
        this.updatedBy = loginUser.getLoginUser();
    }

    public void changeCompleteType(BuyerCompleteDto completeDto) {
        this.completeType = completeDto.getCompleteType();
    }

    public void delete(Administrator loginAdmin) {
        this.deleteYn = YnType.Y;
        this.updatedBy = loginAdmin;
    }
}
