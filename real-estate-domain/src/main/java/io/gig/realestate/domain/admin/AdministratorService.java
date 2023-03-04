package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.role.Role;

import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
public interface AdministratorService {

    long getCountAdministratorData();

    void initAdmin(String username, String password, String name, Set<Role> roles);
}
