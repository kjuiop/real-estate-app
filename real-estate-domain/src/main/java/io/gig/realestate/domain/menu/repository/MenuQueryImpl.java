package io.gig.realestate.domain.menu.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.MenuReader;
import io.gig.realestate.domain.menu.dto.MenuDto;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public Menu findById(Long id) {
        Optional<Menu> optMenu = queryRepository.findById(id);
        if (optMenu.isEmpty())
            throw new NotFoundException(">>> Menu not found");

        return optMenu.get();
    }

    @Override
    public MenuDto getMenuDtoIncludeParent(Long id) {
        Optional<Menu> optMenu = queryRepository.findById(id);
        if (optMenu.isEmpty())
            throw new NotFoundException(">>> Menu not found");

        Menu menu = optMenu.get();
        return MenuDto.includeParent(menu);
    }

    @Override
    public List<Menu> getAllMenuHierarchy(MenuType menuType) {
        return queryRepository.getAllMenuHierarchy(menuType);
    }

    @Override
    public List<Menu> getMenuHierarchyByRoles(MenuType menuType, Set<Role> roles) {
        return queryRepository.getMenuHierarchyByRoles(menuType, roles);
    }

    @Override
    public long getCountMenuData() {
        return queryRepository.getCountMenuData();
    }

}
