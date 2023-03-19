package io.gig.realestate.domain.role;

import io.gig.realestate.domain.exception.AlreadyEntity;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleStore roleStore;
    private final RoleReader roleReader;

    @Override
    @Transactional(readOnly = true)
    public long getCountRoleData() {
        return roleReader.getCountRoleData();
    }

    @Override
    public List<Role> findByRoleNamesIn(List<String> roleNames) {
        return roleReader.findByRoleNamesIn(roleNames);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        return roleReader.findAllByOrderBySortOrderAsc().stream().map(RoleDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByRoleName(String roleName) {
        return roleReader.findByRoleName(roleName);
    }

    @Override
    @Transactional
    public void initRole(String name, String description, int sortOrder) {
        boolean isExist = existsRoleName(name);
        if (isExist) {
            throw new AlreadyEntity("이미 존재하는 권한입니다. Role Name : " + name);
        }

        Role newRole = Role.createRole(name, description, sortOrder);
        roleStore.store(newRole);
    }

    public boolean existsRoleName(String roleName) {
        return roleReader.existsByName(roleName);
    }
}
