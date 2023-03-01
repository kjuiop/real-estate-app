package io.gig.realestate.domain.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorQueryRepository administratorQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public long getCountAdministratorData() {
        return administratorQueryRepository.count();
    }
}
