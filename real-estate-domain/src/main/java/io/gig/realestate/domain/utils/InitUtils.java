package io.gig.realestate.domain.utils;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.exception.AlreadyEntity;
import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.MenuService;
import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.team.Team;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.types.TeamStatus;
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
    private final CategoryService categoryService;
    private final TeamService teamService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {AlreadyEntity.class})
    public void initData() {
        validateAlreadyEntity();
        initAdminRoleAndMenuData();
        initCategoryData();
    }

    private void initAdminRoleAndMenuData() {
        roleService.initRole("ROLE_SUPER_ADMIN", "슈퍼관리자", 0);
        roleService.initRole("ROLE_MANAGER", "팀장", 1);
        roleService.initRole("ROLE_MEMBER", "팀원", 2);

        Role superAdminRole = roleService.findByRoleName("ROLE_SUPER_ADMIN");
        Role managerRole = roleService.findByRoleName("ROLE_MANAGER");
        Role memberRole = roleService.findByRoleName("ROLE_MEMBER");

        Set<Role> superAdminRoles = new HashSet<>();
        superAdminRoles.add(superAdminRole);
        superAdminRoles.add(managerRole);
        Administrator superAdmin = administratorService.initAdmin("admin@jdrealty.io", passwordEncoder.encode("jdrealty123$"), "초기관리자", superAdminRoles);

        Set<Role> superAdminMenuRoles = new HashSet<>();
        superAdminMenuRoles.add(superAdminRole);

        Set<Role> managerMenuRoles = new HashSet<>();
        managerMenuRoles.add(managerRole);

        Set<Role> memberMenuRoles = new HashSet<>();
        memberMenuRoles.add(memberRole);
        memberMenuRoles.add(managerRole);

        menuService.initMenu("Home", "/", "fa fa-home", 0, memberMenuRoles);
        menuService.initMenu("팀 관리", "/team", "fa fa-users", 1, managerMenuRoles);
        menuService.initMenu("매물관리", "/real-estate", "fa fa-building", 2, memberMenuRoles);
        Menu settingMenu = menuService.initMenu("설정", "/settings", "fa fa-gear", 99, superAdminMenuRoles);
        menuService.initChildMenu("메뉴관리", "/settings/menu-manager", "fa fa-circle-o", 1, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("카테고리관리", "/settings/category-manager", "fa fa-circle-o", 2, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("전체 팀 관리", "/settings/team-manager", "fa fa-circle-o", 3, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("관리자관리", "/settings/administrators", "fa fa-circle-o", 4, superAdminMenuRoles, settingMenu);

        teamService.initTeam("본부", TeamStatus.ACTIVE, superAdmin);


        Set<Role> managerRoles = new HashSet<>();
        managerRoles.add(managerRole);
        Administrator devAdmin = administratorService.initAdmin("dev@jdrealty.io", passwordEncoder.encode("dev123$"), "개발팀", managerRoles);
        teamService.initTeam("김정인", TeamStatus.ACTIVE, devAdmin);
    }

    private void initCategoryData() {

        Category processStep = categoryService.initCategory("진행구분", YnType.Y, 1, 1);
        categoryService.initChildCategory("준비", YnType.Y, 2, 1, processStep);
        categoryService.initChildCategory("작업중", YnType.Y, 2, 2, processStep);
        categoryService.initChildCategory("완료", YnType.Y, 2, 3, processStep);
        categoryService.initChildCategory("보류", YnType.Y, 2, 4, processStep);
        categoryService.initChildCategory("매각 전", YnType.Y, 2, 5, processStep);
        categoryService.initChildCategory("매각", YnType.Y, 2, 6, processStep);

        Category usageType = categoryService.initCategory("매물용도", YnType.Y, 1, 2);
        Category usageChange = categoryService.initChildCategory("용도변경-별실가능", YnType.Y, 2, 1, usageType);
        categoryService.initChildCategory("투자용", YnType.Y, 3, 1, usageChange);
        categoryService.initChildCategory("사옥용", YnType.Y, 3, 2, usageChange);
        categoryService.initChildCategory("거주용", YnType.Y, 3, 3, usageChange);
        categoryService.initChildCategory("임대수익용", YnType.Y, 3, 4, usageChange);
        categoryService.initChildCategory("신축-리모델링용", YnType.Y, 3, 5, usageChange);
        categoryService.initChildCategory("모텔/호텔", YnType.Y, 3, 6, usageChange);
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

        if (categoryService.getCountCategoryData() > 0) {
            throw new AlreadyEntity("이미 카테고리 데이터가 존재합니다.");
        }
    }
}
