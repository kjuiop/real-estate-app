package io.gig.realestate.domain.menu;

import io.gig.realestate.domain.menu.dto.MenuCreateForm;
import io.gig.realestate.domain.menu.dto.MenuDto;
import io.gig.realestate.domain.menu.dto.MenuUpdateForm;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;

import java.util.List;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
public interface MenuService {

    Menu initMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles);

    void initChildMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles, Menu parentMenu);

    long getCountMenuData();

    List<MenuDto> getMenuHierarchyByRoles(MenuType adminConsole, Set<Role> roles);

    List<MenuDto> getAllMenuHierarchy(MenuType adminConsole);

    MenuDto getMenuDtoIncludeParent(Long id);

    Long create(MenuCreateForm createForm);

    Long update(MenuUpdateForm updateForm);
}
