package io.gig.realestate.domain.menu.repository;

import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.MenuReader;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
    public List<Menu> getMenuHierarchyByRoles(MenuType menuType, Set<Role> roles) {
        return queryRepository.getMenuHierarchyByRoles(menuType, roles);
    }

    @Override
    public long getCountMenuData() {
        return queryRepository.getCountMenuData();
    }

}
