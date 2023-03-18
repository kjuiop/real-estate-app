package io.gig.realestate.domain.admin.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorReader;
import io.gig.realestate.domain.admin.types.AdminStatus;
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
    public Administrator getAdminFindByUsername(String username) {
        Optional<Administrator> findAdmin = queryRepository.findByUsername(username);
        if (findAdmin.isEmpty()) {
            throw new UsernameNotFoundException("가입되어 있지 않은 이메일 주소입니다.");
        }
        return findAdmin.get();
    }

    @Override
    public long getCountAdministratorData() {
        return queryRepository.count();
    }
}
