package io.gig.realestate.domain.menu.repository;

import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.MenuStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Component
@Transactional
@RequiredArgsConstructor
public class MenuStoreImpl implements MenuStore {

    private final MenuStoreRepository menuStoreRepository;

    @Override
    public Menu store(Menu menu) {
        return menuStoreRepository.save(menu);
    }
}
