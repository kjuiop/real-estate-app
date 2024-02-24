package io.gig.realestate.domain.buyer.repository;

import io.gig.realestate.domain.buyer.Buyer;
import io.gig.realestate.domain.buyer.BuyerStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Component
@Transactional
@RequiredArgsConstructor
public class BuyerStoreImpl implements BuyerStore {

    private final BuyerStoreRepository storeRepository;

    @Override
    public Buyer store(Buyer buyer) {
        return storeRepository.save(buyer);
    }
}
