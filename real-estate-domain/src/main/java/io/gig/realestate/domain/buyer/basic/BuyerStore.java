package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerStore {
    Buyer store(Buyer buyer);

    BuyerDetail storeDetail(BuyerDetail detail);
}
