package io.gig.realestate.domain.buyer.basic.dto;

import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2024/04/02
 */
@Getter
public class BuyerModalDto {

    private final BuyerDetailDto buyerDetail;

    public BuyerModalDto(BuyerDetailDto dto) {
        this.buyerDetail = dto;
    }
}
