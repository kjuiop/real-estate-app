package io.gig.realestate.domain.realestate.manager.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.manager.RealEstateManager;
import io.gig.realestate.domain.realestate.manager.RealEstateManagerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/06/30
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RealEstateManagerQueryImpl implements RealEstateManagerReader {

    private final RealEstateManagerQueryRepository queryRepository;

    @Override
    public Optional<RealEstateManager> getRealEstateManager(RealEstate realEstate, Administrator manager) {
        return queryRepository.getRealEstateManager(realEstate, manager);
    }
}
