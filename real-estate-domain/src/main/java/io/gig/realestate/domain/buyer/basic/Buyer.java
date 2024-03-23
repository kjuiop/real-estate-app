package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.basic.dto.BuyerCreateForm;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String requestDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static Buyer create(BuyerCreateForm createForm, Administrator loginUser) {
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
                .build();
    }

    public void update(LoginUser loginUser) {
        this.updatedBy = loginUser.getLoginUser();
    }
}
