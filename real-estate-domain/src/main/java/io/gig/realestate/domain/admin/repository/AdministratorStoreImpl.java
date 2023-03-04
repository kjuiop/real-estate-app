package io.gig.realestate.domain.admin.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
@Component
@Transactional
@RequiredArgsConstructor
public class AdministratorStoreImpl implements AdministratorStore {

    private final AdministratorStoreRepository administratorStoreRepository;

    @Override
    public Administrator store(Administrator administrator) {
        return administratorStoreRepository.save(administrator);
    }
}
