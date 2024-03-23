package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/02/18
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class BuyerDto {

    private Long buyerId;

    private String title;

    private String usageTypeCds;

    private int successPercent;

    private String name;

    private String managerName;

    private String purposeCds;

    private String loanCharacterCds;

    private String preferBuildingCds;

    private String investmentTimingCds;

    private LocalDateTime createdAt;

    public BuyerDto(Buyer b) {
        this.buyerId = b.getId();
        this.createdAt = b.getCreatedAt();
//        if (b.getBuyerDetails().size() > 0) {
//            BuyerDetail detail = b.getBuyerDetails().get(b.getBuyerDetails().size()-1);
//            this.title = detail.getTitle();
//            this.usageTypeCds = detail.getUsageTypeCds();
//            this.successPercent = detail.getSuccessPercent();
//            this.name = detail.getName();
//            if (detail.getUpdatedBy() != null) {
//                this.managerName = detail.getUpdatedBy().getName();
//            }
//        }
    }
}
