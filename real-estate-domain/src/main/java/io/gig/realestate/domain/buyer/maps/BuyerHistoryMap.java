package io.gig.realestate.domain.buyer.maps;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapForm;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyerHistoryMap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    private String processCds;

    private String processName;

    private String colorCode;

    private int historyCnt;

    private int sortOrder;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static BuyerHistoryMap create(CategoryDto dto, Buyer buyer, Administrator loginUser) {
        return BuyerHistoryMap.builder()
                .buyer(buyer)
                .processCds(dto.getCode())
                .processName(dto.getName())
                .colorCode(dto.getColorCode())
                .sortOrder(dto.getSortOrder())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public static BuyerHistoryMap createCustomMap(Buyer buyer, HistoryMapForm createForm, Administrator loginUser) {
        return BuyerHistoryMap.builder()
                .buyer(buyer)
                .processName(createForm.getProcessName())
                .colorCode(createForm.getColorCode())
                .sortOrder(createForm.getSortOrder())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void increaseHistoryCnt() {
        this.historyCnt++;
    }
}
