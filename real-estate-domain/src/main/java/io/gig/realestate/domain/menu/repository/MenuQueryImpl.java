package io.gig.realestate.domain.menu.repository;

import io.gig.realestate.domain.menu.MenuReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuQueryImpl implements MenuReader {

    private final MenuQueryRepository queryRepository;

    @Override
    public long getCountMenuData() {
        return queryRepository.getCountMenuData();
    }

}
