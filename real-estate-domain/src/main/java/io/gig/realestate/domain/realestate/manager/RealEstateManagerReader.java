package io.gig.realestate.domain.realestate.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.realestate.basic.RealEstate;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/06/30
 */
public interface RealEstateManagerReader {
    Optional<RealEstateManager> getRealEstateManager(RealEstate realEstate, Administrator manager);
}
