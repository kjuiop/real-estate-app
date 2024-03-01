package io.gig.realestate.domain.buyer.dto;

import io.gig.realestate.domain.buyer.Buyer;
import io.gig.realestate.domain.buyer.BuyerDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    private String managerName;

    public BuyerDto(Buyer b) {
        this.buyerId = b.getId();
        if (b.getBuyerDetails().size() > 0) {
            BuyerDetail detail = b.getBuyerDetails().get(b.getBuyerDetails().size()-1);
            this.title = detail.getTitle();
            this.usageTypeCds = detail.getUsageTypeCds();
            this.successPercent = detail.getSuccessPercent();
            if (detail.getUpdatedBy() != null) {
                this.managerName = detail.getUpdatedBy().getName();
            }
        }
    }
}
