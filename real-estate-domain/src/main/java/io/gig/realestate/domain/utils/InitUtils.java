package io.gig.realestate.domain.utils;

import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.exception.AlreadyEntity;
import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Component
@RequiredArgsConstructor
public class InitUtils {

    private final RoleService roleService;
    private final AdministratorService administratorService;

    public void initData() {
        validateAlreadyEntity();

        roleService.initRole("ROLE_SUPER_ADMIN", "슈퍼관리자", 0);
        roleService.initRole("ROLE_ADMIN", "관리자", 1);
        roleService.initRole("ROLE_USER", "사용자", 2);
        roleService.initRole("ROLE_ANONYMOUS", "익명사용자", 3);

        Role superAdminRole = roleService.findByRoleName("ROLE_SUPER_ADMIN");
        Role adminRole = roleService.findByRoleName("ROLE_ADMIN");

    }


    private void validateAlreadyEntity() {

        if (roleService.getCountRoleData() > 0) {
            throw new AlreadyEntity("이미 권한 데이터가 존재합니다.");
        }

        if (administratorService.getCountAdministratorData() > 0) {
            throw new AlreadyEntity("이미 관리자 데이터가 존재합니다.");
        }
    }
}
