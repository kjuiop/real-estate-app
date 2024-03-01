package io.gig.realestate.domain.buyer.detail.repository;

import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.detail.BuyerDetailStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
@Component
@Transactional
@RequiredArgsConstructor
public class BuyerDetailStoreImpl implements BuyerDetailStore {

    private final BuyerDetailStoreRepository storeRepository;

    @Override
    public BuyerDetail store(BuyerDetail buyerDetail) {
        return storeRepository.save(buyerDetail);
    }
}
