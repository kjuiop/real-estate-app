package io.gig.realestate.domain.realestate.repository;

import io.gig.realestate.domain.realestate.RealEstate;
import io.gig.realestate.domain.realestate.RealEstateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Component
@Transactional
@RequiredArgsConstructor
public class RealEstateStoreImpl implements RealEstateStore {

    private final RealEstateStoreRepository storeRepository;

    @Override
    public RealEstate store(RealEstate newRealEstate) {
        return storeRepository.save(newRealEstate);
    }
}
