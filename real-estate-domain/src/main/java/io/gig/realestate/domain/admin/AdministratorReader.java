package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
public interface AdministratorReader {

    long getCountAdministratorData();

    AdministratorDetailDto getAdminFindByUsername(String username);

    Administrator getAdministratorEntityByUsername(String username);

    Administrator getAdminEntityByUsername(String username);
}
