package io.gig.realestate.domain.menu;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.menu.types.AntMatcherType;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Entity
@SuperBuilder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType displayYn = YnType.Y;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType activeYn = YnType.Y;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AntMatcherType antMatcherType = AntMatcherType.Single;

    @Builder.Default
    private int sortOrder = 0;

    private String iconClass;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")
    private Menu parent;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ")
    @JoinColumn(name = "PARENT_ID")
    private List<Menu> children = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "MENU_ROLE",
            joinColumns = @JoinColumn(name = "MENU_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_NAME")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static Menu initMenu(String name, String url, String iconClass, int sortOrder, Set<Role> roles) {
        return Menu.builder()
                .menuType(MenuType.AdminConsole)
                .name(name)
                .url(url)
                .iconClass(iconClass)
                .sortOrder(sortOrder)
                .roles(roles)
                .build();
    }

    public void addParent(Menu parent) {
        this.parent = parent;
        this.menuType = parent.getMenuType();
        this.antMatcherType = AntMatcherType.All;
        parent.getChildren().add(this);
        parent.antMatcherType = AntMatcherType.Single;
    }
}
