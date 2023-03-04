package io.gig.realestate.domain.admin.repository;

import io.gig.realestate.domain.admin.AdministratorReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public long getCountAdministratorData() {
        return queryRepository.count();
    }
}
