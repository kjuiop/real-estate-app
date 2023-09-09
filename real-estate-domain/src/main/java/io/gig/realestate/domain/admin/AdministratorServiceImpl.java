package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.dto.*;
import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
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
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<AdministratorListDto> getAdminPageListBySearch(AdminSearchDto searchDto) {
        return administratorReader.getAdminPageListBySearch(searchDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AdministratorDetailDto getDetail(Long adminId) {
        return administratorReader.getAdminDetail(adminId);
    }

    @Override
    @Transactional
    public Long create(@NotNull AdministratorCreateForm createForm) {
        Administrator newAdmin = Administrator.create(createForm, passwordEncoder.encode(createForm.getPassword()));
        List<Role> roles = roleService.findByRoleNamesIn(createForm.getRoleNames());
        newAdmin.createAdministratorRoles(roles);
        Administrator savedAdmin = administratorStore.store(newAdmin);
        return savedAdmin.getId();
    }

    @Override
    public Long update(AdministratorUpdateForm updateForm) {
        Administrator administrator = getAdminEntityByUsername(updateForm.getUsername());
        if (!StringUtils.hasText(updateForm.getPassword())) {
            validPassword(administrator, updateForm.getPassword());
        }
        administrator.update(updateForm, passwordEncoder.encode(updateForm.getPassword()));
        List<Role> roles = roleService.findByRoleNamesIn(updateForm.getRoleNames());
        administrator.updateAdministratorRoles(roles);
        return administratorStore.store(administrator).getId();
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

    @Override
    @Transactional
    public List<AdministratorListDto> getNotTeamAdmins() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public AdministratorDetailDto getAdminFindByUsername(String username) {
        return administratorReader.getAdminFindByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrator getAdminEntityByUsername(String username) {
        return administratorReader.getAdminEntityByUsername(username);
    }

    @Override
    @Transactional
    public void loginSuccess(String username) {
        Administrator findAdministrator = administratorReader.getAdministratorEntityByUsername(username);
        findAdministrator.loginSuccess();
    }

    @Override
    @Transactional
    public void increasePasswordFailureCount(String username) {
        Administrator findAdministrator = administratorReader.getAdministratorEntityByUsername(username);
        findAdministrator.increasePasswordFailureCount();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountAdministratorData() {
        return administratorReader.getCountAdministratorData();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUsername(String value) {
        return administratorReader.existUsername(value);
    }

    private void validPassword(Administrator administrator, String password) {
        if (administrator.passwordValid(password)) {
            throw new IllegalArgumentException("이전에 사용했던 비밀번호입니다.");
        }
    }
}
