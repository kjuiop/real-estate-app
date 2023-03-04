package io.gig.realestate.domain.role;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
public interface RoleService {

    long getCountRoleData();

    void initRole(String name, String description, int sortOrder);
}
