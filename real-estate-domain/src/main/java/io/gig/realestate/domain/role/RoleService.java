package io.gig.realestate.domain.role;

import io.gig.realestate.domain.role.dto.RoleDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
public interface RoleService {

    long getCountRoleData();

    void initRole(String name, String description, int sortOrder);

    Role findByRoleName(String roleName);

    List<RoleDto> getAllRoles();
}
