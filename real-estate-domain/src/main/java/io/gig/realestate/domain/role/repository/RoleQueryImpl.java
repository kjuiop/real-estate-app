package io.gig.realestate.domain.role.repository;

import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.role.RoleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleQueryImpl implements RoleReader {

    private final RoleQueryRepository queryRepository;

    @Override
    public List<Role> findAllByOrderBySortOrderAsc() {
        return queryRepository.findAllByOrderBySortOrderAsc();
    }

    @Override
    public long getCountRoleData() {
        return queryRepository.count();
    }

    @Override
    public Role findByRoleName(String roleName) {
        return queryRepository.findByName(roleName);
    }

    @Override
    public boolean existsByName(String name) {
        return queryRepository.existsByName(name);
    }
}
