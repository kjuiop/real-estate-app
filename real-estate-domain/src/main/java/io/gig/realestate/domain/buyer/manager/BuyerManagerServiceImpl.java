package io.gig.realestate.domain.buyer.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/04/07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuyerManagerServiceImpl implements BuyerManagerService {

    private final BuyerManagerReader buyerManagerReader;

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyerManager> getBuyerManager(Buyer buyer, Administrator manager) {
        return buyerManagerReader.getBuyerManager(buyer, manager);
    }
}
