package io.gig.realestate.domain.role.repository;

import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.role.RoleStore;
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
public class RoleStoreImpl implements RoleStore {

    private final RoleStoreRepository roleStoreRepository;

    @Override
    public Role store(Role role) {
        return roleStoreRepository.save(role);
    }
}
