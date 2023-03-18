package io.gig.realestate.domain.admin;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
public interface AdministratorReader {

    long getCountAdministratorData();

    Administrator getAdminFindByUsername(String username);
}
