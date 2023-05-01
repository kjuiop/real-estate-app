package io.gig.realestate.domain.utils;

import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.exception.AlreadyEntity;
import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.MenuService;
import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Component
@RequiredArgsConstructor
public class InitUtils {

    private final MenuService menuService;
    private final RoleService roleService;
    private final AdministratorService administratorService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {AlreadyEntity.class})
    public void initData() {
        validateAlreadyEntity();

        roleService.initRole("ROLE_SUPER_ADMIN", "슈퍼관리자", 0);
        roleService.initRole("ROLE_ADMIN", "관리자", 1);
        roleService.initRole("ROLE_USER", "사용자", 2);
        roleService.initRole("ROLE_ANONYMOUS", "익명사용자", 3);

        Role superAdminRole = roleService.findByRoleName("ROLE_SUPER_ADMIN");
        Role adminRole = roleService.findByRoleName("ROLE_ADMIN");

        Set<Role> superAdminRoles = new HashSet<>();
        superAdminRoles.add(superAdminRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(superAdminRole);
        adminRoles.add(adminRole);


        administratorService.initAdmin("admin@citylight.io", passwordEncoder.encode("citylight123$"), "초기관리자", superAdminRoles);
        menuService.initMenu("Home", "/", "fa fa-home", 0, superAdminRoles);
        menuService.initMenu("관리자관리", "/administrators", "fa fa-users", 0, superAdminRoles);
        menuService.initMenu("매물관리", "/real-estate", "fa fa-building", 0, adminRoles);
        Menu settingMenu = menuService.initMenu("설정", "/settings", "fa fa-gear", 99, superAdminRoles);
        menuService.initChildMenu("메뉴관리", "/settings/menu-manager", "fa fa-circle-o", 1, superAdminRoles, settingMenu);
        menuService.initChildMenu("카테고리관리", "/settings/category-manager", "fa fa-circle-o", 2, superAdminRoles, settingMenu);
    }


    private void validateAlreadyEntity() {

        if (roleService.getCountRoleData() > 0) {
            throw new AlreadyEntity("이미 권한 데이터가 존재합니다.");
        }

        if (administratorService.getCountAdministratorData() > 0) {
            throw new AlreadyEntity("이미 관리자 데이터가 존재합니다.");
        }

        if (menuService.getCountMenuData() > 0) {
            throw new AlreadyEntity("이미 메뉴 데이터가 존재합니다.");
        }
    }
}
