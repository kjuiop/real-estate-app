package io.gig.realestate.domain.realestate.basic;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
public interface RealEstateStore {
    RealEstate store(RealEstate newRealEstate);

    void storeAll(List<RealEstate> realEstateList);
}
