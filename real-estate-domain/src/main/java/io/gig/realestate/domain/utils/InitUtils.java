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
        Administrator superAdmin = administratorService.initAdmin("admin@hanshin.io", passwordEncoder.encode("admin123$"), "초기관리자", "010-3182-0825", superAdminRoles);

        Set<Role> superAdminMenuRoles = new HashSet<>();
        superAdminMenuRoles.add(superAdminRole);

        Set<Role> managerMenuRoles = new HashSet<>();
        managerMenuRoles.add(managerRole);

        Set<Role> memberMenuRoles = new HashSet<>();
        memberMenuRoles.add(memberRole);
        memberMenuRoles.add(managerRole);

        menuService.initMenu("Home", "/", "fa fa-home", 0, memberMenuRoles);
        menuService.initMenu("팀 관리", "/team", "fa fa-users", 1, managerMenuRoles);
        menuService.initMenu("지도검색", "/map", "fa fa-map", 2, memberMenuRoles);
        menuService.initMenu("매수자관리", "/buyer", "fa fa-money", 3, memberMenuRoles);
        menuService.initMenu("매물관리", "/real-estate", "fa fa-building", 4, memberMenuRoles);
        Menu settingMenu = menuService.initMenu("설정", "/settings", "fa fa-gear", 99, superAdminMenuRoles);
        menuService.initChildMenu("메뉴관리", "/settings/menu-manager", "fa fa-circle-o", 1, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("카테고리관리", "/settings/category-manager", "fa fa-circle-o", 2, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("전체 팀 관리", "/settings/team-manager", "fa fa-circle-o", 3, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("관리자관리", "/settings/administrators", "fa fa-circle-o", 4, superAdminMenuRoles, settingMenu);
        menuService.initChildMenu("법정동 코드관리", "/settings/area-manager", "fa fa-circle-o", 5, superAdminMenuRoles, settingMenu);

        teamService.initTeam("본부", YnType.Y, superAdmin);


        Set<Role> managerRoles = new HashSet<>();
        managerRoles.add(managerRole);
        Administrator devAdmin = administratorService.initAdmin("dev@jdrealty.io", passwordEncoder.encode("dev123$"), "김정인", "010-3182-0825", managerRoles);
        teamService.initTeam("개발팀", YnType.Y, devAdmin);
    }

    private void initCategoryData() {
        Category usageType = categoryService.initCategory("CD_USAGE","매물용도", YnType.Y, 1, 1);
        Category usageChange = categoryService.initChildCategory("CD_USAGE_01", "CD_USAGE", "용도변경-멸실가능", YnType.Y, 2, 1, usageType);
        categoryService.initChildCategory("CD_USAGE_01_01", "CD_USAGE_01", "투자용", YnType.Y, 3, 1, usageChange);
        categoryService.initChildCategory("CD_USAGE_01_02", "CD_USAGE_01", "사옥용", YnType.Y, 3, 2, usageChange);
        categoryService.initChildCategory("CD_USAGE_01_03", "CD_USAGE_01", "거주용", YnType.Y, 3, 3, usageChange);
        categoryService.initChildCategory("CD_USAGE_01_04", "CD_USAGE_01", "임대수익용", YnType.Y, 3, 4, usageChange);
        categoryService.initChildCategory("CD_USAGE_01_05", "CD_USAGE_01", "신축-리모델링용", YnType.Y, 3, 5, usageChange);
        categoryService.initChildCategory("CD_USAGE_01_06", "CD_USAGE_01", "모텔/호텔", YnType.Y, 3, 6, usageChange);

        Category landType = categoryService.initCategory("CD_LAND", "매물유형", YnType.Y, 1, 2);
        categoryService.initChildCategory("CD_LAND_01", "CD_LAND_01", "일반", YnType.Y, 2, 1, landType);
        categoryService.initChildCategory("CD_LAND_02", "CD_LAND_02", "일반", YnType.Y, 2, 1, landType);

        Category processType = categoryService.initCategory("CD_PROCESS", "진행단계", YnType.Y, 1, 3);
        categoryService.initChildCategory("CD_PROCESS_01", "CD_PROCESS", "전화", YnType.Y, 2, 1, processType);
        categoryService.initChildCategory("CD_PROCESS_02", "CD_PROCESS", "카톡", YnType.Y, 2, 2, processType);
        categoryService.initChildCategory("CD_PROCESS_03", "CD_PROCESS", "미팅", YnType.Y, 2, 3, processType);
        categoryService.initChildCategory("CD_PROCESS_04", "CD_PROCESS", "답사", YnType.Y, 2, 4, processType);
        categoryService.initChildCategory("CD_PROCESS_05", "CD_PROCESS", "조율", YnType.Y, 2, 5, processType);
        categoryService.initChildCategory("CD_PROCESS_06", "CD_PROCESS", "계약", YnType.Y, 2, 6, processType);
        categoryService.initChildCategory("CD_PROCESS_07", "CD_PROCESS", "잔금", YnType.Y, 2, 7, processType);

        Category investmentCharacter = categoryService.initCategory("CD_INVESTMENT_CHARACTER", "투자성향", YnType.Y, 1, 4);
        categoryService.initChildCategory("CD_INVESTMENT_CHARACTER_01", "CD_INVESTMENT_CHARACTER", "수익률", YnType.Y, 2, 1, investmentCharacter);
        categoryService.initChildCategory("CD_INVESTMENT_CHARACTER_02", "CD_INVESTMENT_CHARACTER", "지가상승기대", YnType.Y, 2, 2, investmentCharacter);
        categoryService.initChildCategory("CD_INVESTMENT_CHARACTER_03", "CD_INVESTMENT_CHARACTER", "입지", YnType.Y, 2, 3, investmentCharacter);
        categoryService.initChildCategory("CD_INVESTMENT_CHARACTER_04", "CD_INVESTMENT_CHARACTER", "직접사용의 편의성", YnType.Y, 2, 4, investmentCharacter);

        Category exclusiveType = categoryService.initCategory("CD_EXCLUSIVE", "전속유형", YnType.Y, 1, 5);
        categoryService.initChildCategory("CD_EXCLUSIVE_01", "CD_EXCLUSIVE", "일반", YnType.Y, 2, 1, exclusiveType);
        categoryService.initChildCategory("CD_EXCLUSIVE_02", "CD_EXCLUSIVE", "공동", YnType.Y, 2, 2, exclusiveType);
        categoryService.initChildCategory("CD_EXCLUSIVE_03", "CD_EXCLUSIVE", "전속", YnType.Y, 2, 3, exclusiveType);
        categoryService.initChildCategory("CD_EXCLUSIVE_04", "CD_EXCLUSIVE", "타전속", YnType.Y, 2, 4, exclusiveType);

        Category buyerGradeType = categoryService.initCategory("CD_BUYER_GRADE", "매수자등급", YnType.Y, 1, 6);
        categoryService.initChildCategory("CD_BUYER_GRADE_01", "CD_BUYER_GRADE", "A", YnType.Y, 2, 1, buyerGradeType);
        categoryService.initChildCategory("CD_BUYER_GRADE_02", "CD_BUYER_GRADE", "B", YnType.Y, 2, 2, buyerGradeType);
        categoryService.initChildCategory("CD_BUYER_GRADE_03", "CD_BUYER_GRADE", "C", YnType.Y, 2, 3, buyerGradeType);
        categoryService.initChildCategory("CD_BUYER_GRADE_04", "CD_BUYER_GRADE", "D", YnType.Y, 2, 4, buyerGradeType);
        categoryService.initChildCategory("CD_BUYER_GRADE_05", "CD_BUYER_GRADE", "E", YnType.Y, 2, 5, buyerGradeType);

        Category purposeType = categoryService.initCategory("CD_PURPOSE", "매물목적", YnType.Y, 1, 7);
        categoryService.initChildCategory("CD_PURPOSE_01", "CD_PURPOSE", "시세투자", YnType.Y, 2, 1, purposeType);
        categoryService.initChildCategory("CD_PURPOSE_02", "CD_PURPOSE", "사옥", YnType.Y, 2, 2, purposeType);
        categoryService.initChildCategory("CD_PURPOSE_03", "CD_PURPOSE", "반반", YnType.Y, 2, 3, purposeType);
        categoryService.initChildCategory("CD_PURPOSE_04", "CD_PURPOSE", "수익률 굿", YnType.Y, 2, 4, purposeType);
        categoryService.initChildCategory("CD_PURPOSE_05", "CD_PURPOSE", "신축용", YnType.Y, 2, 5, purposeType);
        categoryService.initChildCategory("CD_PURPOSE_06", "CD_PURPOSE", "리모델링용", YnType.Y, 2, 6, purposeType);

        Category preferBuilding = categoryService.initCategory("CD_PREFER_BUILDING", "선호빌딩", YnType.Y, 1, 8);
        categoryService.initChildCategory("CD_PREFER_BUILDING_01", "CD_PREFER_BUILDING", "수익률", YnType.Y, 2, 1, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_02", "CD_PREFER_BUILDING", "지가상승기대", YnType.Y, 2, 2, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_03", "CD_PREFER_BUILDING", "입지", YnType.Y, 2, 3, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_04", "CD_PREFER_BUILDING", "직접사용의 편의성", YnType.Y, 2, 4, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_05", "CD_PREFER_BUILDING", "도로변", YnType.Y, 2, 5, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_06", "CD_PREFER_BUILDING", "신축빌딩", YnType.Y, 2, 6, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_07", "CD_PREFER_BUILDING", "급매물", YnType.Y, 2, 7, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_08", "CD_PREFER_BUILDING", "가성비", YnType.Y, 2, 8, preferBuilding);
        categoryService.initChildCategory("CD_PREFER_BUILDING_09", "CD_PREFER_BUILDING", "직접판매가능", YnType.Y, 2, 9, preferBuilding);

        Category investmentTiming = categoryService.initCategory("CD_INVESTMENT_TIMING", "투자시기", YnType.Y, 1, 9);
        categoryService.initChildCategory("CD_INVESTMENT_TIMING_01", "CD_INVESTMENT_TIMING", "즉시", YnType.Y, 2, 1, investmentTiming);
        categoryService.initChildCategory("CD_INVESTMENT_TIMING_02", "CD_INVESTMENT_TIMING", "3개월이내", YnType.Y, 2, 2, investmentTiming);
        categoryService.initChildCategory("CD_INVESTMENT_TIMING_03", "CD_INVESTMENT_TIMING", "3~6개월", YnType.Y, 2, 3, investmentTiming);
        categoryService.initChildCategory("CD_INVESTMENT_TIMING_04", "CD_INVESTMENT_TIMING", "6개월이상", YnType.Y, 2, 4, investmentTiming);
        categoryService.initChildCategory("CD_INVESTMENT_TIMING_05", "CD_INVESTMENT_TIMING", "급매물기다림", YnType.Y, 2, 5, investmentTiming);
        categoryService.initChildCategory("CD_INVESTMENT_TIMING_06", "CD_INVESTMENT_TIMING", "오래걸림", YnType.Y, 2, 6, investmentTiming);

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
