package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorCreateForm;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.role.Role;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
public interface AdministratorService {

    long getCountAdministratorData();

    void initAdmin(String username, String password, String name, Set<Role> roles);

    AdministratorDetailDto getAdminFindByUsername(String username);

    void increasePasswordFailureCount(String username);

    void loginSuccess(String username);

    Administrator getAdminEntityByUsername(String username);

    Page<AdministratorListDto> getAdminPageListBySearch(AdminSearchDto searchDto);

    Long create(AdministratorCreateForm createForm);
}
