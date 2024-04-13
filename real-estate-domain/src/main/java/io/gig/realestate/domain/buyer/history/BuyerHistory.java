package io.gig.realestate.domain.buyer.history;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.history.dto.HistoryForm;
import io.gig.realestate.domain.buyer.realestate.HistoryRealEstate;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/03
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyerHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    private String processCds;

    private String processName;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @Builder.Default
    @OneToMany(mappedBy = "buyerHistory", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<HistoryRealEstate> realEstateList = new ArrayList<>();

    public void addHistoryRealEstate(HistoryRealEstate historyRealEstate) {
        this.realEstateList.add(historyRealEstate);
    }

    public static BuyerHistory create(HistoryForm createForm, Buyer buyer, Administrator loginUser) {
        return BuyerHistory.builder()
                .processCds(createForm.getProcessCds())
                .processName(createForm.getProcessName())
                .memo(createForm.getMemo())
                .buyer(buyer)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }
}
