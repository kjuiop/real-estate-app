package io.gig.realestate.domain.buyer.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/04/07
 */
public interface BuyerManagerService {
    Optional<BuyerManager> getBuyerManager(Buyer buyer, Administrator manager);
}
