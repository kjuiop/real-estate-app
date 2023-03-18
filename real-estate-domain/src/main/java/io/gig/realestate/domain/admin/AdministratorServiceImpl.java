package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorReader administratorReader;
    private final AdministratorStore administratorStore;

    @Override
    public Administrator getAdminFindByUsername(String username) {
        return administratorReader.getAdminFindByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountAdministratorData() {
        return administratorReader.getCountAdministratorData();
    }

    @Override
    @Transactional
    public void initAdmin(String username, String password, String name, Set<Role> roles) {
        Administrator initAdministrator = Administrator.initAdministrator(username, password, name);
        for (Role role : roles) {
            AdministratorRole newRole = AdministratorRole.addAdministratorRole(initAdministrator, role);
            initAdministrator.addRole(newRole);
        }
        administratorStore.store(initAdministrator);
    }
}
