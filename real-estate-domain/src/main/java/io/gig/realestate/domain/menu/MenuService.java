package io.gig.realestate.domain.menu;

import io.gig.realestate.domain.role.Role;

import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
public interface MenuService {

    Menu initMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles);

    void initChildMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles, Menu parentMenu);

    long getCountMenuData();
}
