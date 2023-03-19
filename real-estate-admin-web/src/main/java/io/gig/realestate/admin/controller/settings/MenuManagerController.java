package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.domain.menu.MenuService;
import io.gig.realestate.domain.menu.dto.MenuDto;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/20
 */
@Controller
@RequestMapping("settings/menu-manager")
@RequiredArgsConstructor
public class MenuManagerController {

    private final MenuService menuService;
    private final RoleService roleService;

    @GetMapping
    public String index(Model model) {

        List<MenuDto> acMenus = menuService.getAllMenuHierarchy(MenuType.AdminConsole);
        List<RoleDto> roles = roleService.getAllRoles();

        model.addAttribute("acMenus", acMenus);
        model.addAttribute("roles", roles);

        return "settings/menu/menu-manager";
    }

}
