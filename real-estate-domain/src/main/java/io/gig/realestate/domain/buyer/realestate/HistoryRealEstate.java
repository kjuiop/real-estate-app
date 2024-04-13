package io.gig.realestate.domain.buyer.realestate;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.history.BuyerHistory;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/04/10
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryRealEstate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_history_id")
    private BuyerHistory buyerHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static HistoryRealEstate create(RealEstate realEstate, BuyerHistory buyerHistory, Administrator loginUser) {
        return HistoryRealEstate.builder()
                .realEstate(realEstate)
                .buyerHistory(buyerHistory)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }
}
