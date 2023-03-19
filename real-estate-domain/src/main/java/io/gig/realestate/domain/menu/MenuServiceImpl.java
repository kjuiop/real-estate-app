package io.gig.realestate.domain.menu;

import io.gig.realestate.domain.menu.dto.MenuDto;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuReader menuReader;
    private final MenuStore menuStore;

    @Override
    @Transactional(readOnly = true)
    public List<MenuDto> getMenuHierarchyByRoles(MenuType menuType, Set<Role> roles) {
        List<Menu> topMenus = menuReader.getMenuHierarchyByRoles(menuType, roles);
        List<MenuDto> hierarchy = new ArrayList<>();
        for (Menu m : topMenus) {
            hierarchy.add(new MenuDto(m, true));
        }
        return hierarchy;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDto> getAllMenuHierarchy(MenuType menuType) {
        List<Menu> topMenus = menuReader.getAllMenuHierarchy(menuType);
        List<MenuDto> hierarchy = new ArrayList<>();
        for (Menu m : topMenus) {
            hierarchy.add(new MenuDto(m, true));
        }
        return hierarchy;
    }

    @Override
    @Transactional
    public Menu initMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles) {
        Menu newMenu = Menu.initMenu(name, url, iconClass, sortOrder, roles);
        return menuStore.store(newMenu);
    }

    @Override
    @Transactional
    public void initChildMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles, Menu parentMenu) {
        Menu newMenu = Menu.initMenu(name, url, iconClass, sortOrder, roles);
        newMenu.addParent(parentMenu);
        menuStore.store(newMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountMenuData() {
        return menuReader.getCountMenuData();
    }
}
