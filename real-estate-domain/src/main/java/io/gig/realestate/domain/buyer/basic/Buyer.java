package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
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

    private String title;

    private String name;

    private String usageTypeCds;

    private int successPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_cd_id")
    private Category processCd;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<BuyerDetail> buyerDetails = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static Buyer create(BuyerCreateForm createForm, Category processCd, Administrator loginUser) {
        return Buyer.builder()
                .title(createForm.getTitle())
                .processCd(processCd)
                .usageTypeCds(createForm.getUsageTypeCds())
                .name(createForm.getName())
                .successPercent(createForm.getSuccessPercent())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void setProcessCd(Category processCd) {
        this.processCd = processCd;
    }

    public void addDetail(BuyerDetail buyerDetail) {
        this.buyerDetails.add(buyerDetail);
    }

    public void update(LoginUser loginUser) {
        this.updatedBy = loginUser.getLoginUser();
    }
}
