package io.gig.realestate.domain.buyer.manager.dto;

import io.gig.realestate.domain.buyer.manager.BuyerManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/04/06
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class BuyerManagerDto {

    private Long buyerManagerId;

    private String username;

    private String name;

    private Long adminId;

    public BuyerManagerDto(BuyerManager b) {
        this.buyerManagerId = b.getId();
        this.username = b.getUsername();
        this.name = b.getName();
        this.adminId = b.getAdmin().getId();
    }
}
