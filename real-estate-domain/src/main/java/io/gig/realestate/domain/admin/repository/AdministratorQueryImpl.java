package io.gig.realestate.domain.admin.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorReader;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdministratorQueryImpl implements AdministratorReader {

    private final AdministratorQueryRepository queryRepository;


    @Override
    public AdministratorDetailDto getAdminFindByUsername(String username) {
        Optional<AdministratorDetailDto> findAdministrator = queryRepository.getAdminByUsername(username);
        if (findAdministrator.isEmpty()) {
            throw new UsernameNotFoundException(username + " 계정은 가입되어 있지 않습니다.");
        }
        return findAdministrator.get();
    }

    @Override
    public Administrator getAdministratorEntityByUsername(String username) {
        Optional<Administrator> findAdministrator = queryRepository.getAdministratorEntityByUsername(username);
        if (findAdministrator.isEmpty()) {
            throw new UsernameNotFoundException(username + " 계정은 가입되어 있지 않습니다.");
        }

        return findAdministrator.get();
    }

    @Override
    public Administrator getAdminEntityByUsername(String username) {
        Optional<Administrator> findAdministrator = queryRepository.getAdminEntityByUsername(username);
        if (findAdministrator.isEmpty()) {
            throw new UsernameNotFoundException(username + " 계정은 가입되어 있지 않습니다.");
        }
        return findAdministrator.get();
    }

    @Override
    public long getCountAdministratorData() {
        return queryRepository.getCountAdministrators();
    }
}
