package io.gig.realestate.domain.realestate.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/06/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateManagerServiceImpl implements RealEstateManagerService {

    private final RealEstateManagerReader realEstateManagerReader;

    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateManager> getRealEstateManager(RealEstate realEstate, Administrator manager) {
        return realEstateManagerReader.getRealEstateManager(realEstate, manager);
    }
}
