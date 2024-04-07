package io.gig.realestate.domain.buyer.manager.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.buyer.manager.BuyerManagerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/04/07
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuyerManagerQueryImpl implements BuyerManagerReader {

    private final BuyerManagerQueryRepository queryRepository;

    @Override
    public Optional<BuyerManager> getBuyerManager(Buyer buyer, Administrator manager) {
        return queryRepository.getBuyerManager(buyer, manager);
    }
}
