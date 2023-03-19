package io.gig.realestate.domain.menu;

import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;

import java.util.List;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
public interface MenuReader {

    long getCountMenuData();

    List<Menu> getMenuHierarchyByRoles(MenuType menuType, Set<Role> roles);
}
