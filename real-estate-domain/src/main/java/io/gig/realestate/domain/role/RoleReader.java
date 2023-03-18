package io.gig.realestate.domain.role;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
public interface RoleReader {

    long getCountRoleData();

    Role findByRoleName(String roleName);

    boolean existsByName(String roleName);
}
