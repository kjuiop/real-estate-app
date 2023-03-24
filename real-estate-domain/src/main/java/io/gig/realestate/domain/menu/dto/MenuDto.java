package io.gig.realestate.domain.menu.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.types.AntMatcherType;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private Long id;

    private String name;

    private String url;

    private String iconClass;

    private YnType activeYn;

    private YnType displayYn;

    private int sortOrder;

    private AntMatcherType antMatcherType;

    private MenuType menuType;

    private List<MenuDto> children = new ArrayList<>();

    private List<RoleDto> roles = new ArrayList<>();

    private MenuDto parent;

    private String activeClass;

    public MenuDto(Menu m) {
        this.id = m.getId();
        this.name = m.getName();
        this.url = m.getUrl();
        this.iconClass = m.getIconClass();
        this.activeYn = m.getActiveYn();
        this.displayYn = m.getDisplayYn();
        this.sortOrder = m.getSortOrder();
        this.antMatcherType = m.getAntMatcherType();
        this.menuType = m.getMenuType();
        this.roles = m.getRoles().stream().map(RoleDto::new).collect(Collectors.toList());
    }

    public MenuDto(Menu menu, boolean makeChildren) {
        this(menu);
        if (makeChildren && !CollectionUtils.isEmpty(menu.getChildren())) {
            for (Menu c : menu.getChildren()) {
                if (c.isUsed())
                    this.children.add(new MenuDto(c, true));
            }
        }
    }

    public static MenuDto includeParent(Menu menu) {
        MenuDto menuDto = new MenuDto(menu);
        if (menu.existParent()) {
            menuDto.setParent(new MenuDto(menu.getParent()));
        }
        return menuDto;
    }
}
