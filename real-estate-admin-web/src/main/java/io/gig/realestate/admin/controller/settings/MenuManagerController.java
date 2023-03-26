package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.menu.MenuService;
import io.gig.realestate.domain.menu.dto.MenuCreateForm;
import io.gig.realestate.domain.menu.dto.MenuDto;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @GetMapping("menu/{id}")
    public ResponseEntity<ApiResponse> getAjaxMenu(@PathVariable(name = "id") Long id) {
        MenuDto dto = menuService.getMenuDtoIncludeParent(id);
        return new ResponseEntity<>(ApiResponse.OK(dto), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody MenuCreateForm createForm) {
        Long menuId = menuService.create(createForm);
        return new ResponseEntity<>(ApiResponse.OK(menuId), HttpStatus.OK);
    }

}
