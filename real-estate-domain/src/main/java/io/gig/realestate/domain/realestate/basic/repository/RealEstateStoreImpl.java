package io.gig.realestate.domain.realestate.basic.repository;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public void storeAll(List<RealEstate> realEstateList) {
        storeRepository.saveAll(realEstateList);
    }
}
