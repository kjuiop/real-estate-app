package io.gig.realestate.domain.buyer.dto;

import io.gig.realestate.domain.buyer.Buyer;
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

    public BuyerDto(Buyer b) {
        this.buyerId = b.getId();
        this.title = b.getTitle();

    }
}
