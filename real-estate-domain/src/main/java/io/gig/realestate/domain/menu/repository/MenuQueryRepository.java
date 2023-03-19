package io.gig.realestate.domain.menu.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.menu.Menu;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static io.gig.realestate.domain.menu.QMenu.menu;
import static io.gig.realestate.domain.role.QRole.role;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Menu> getMenuHierarchyByRoles(MenuType menuType, Set<Role> roles) {
        return queryFactory.selectDistinct(menu)
                .from(menu)
                .join(menu.roles, role).fetchJoin()
                .where(defaultCondition())
                .where(menu.parent.isNull())
                .where(menu.menuType.eq(menuType))
                .where(menu.activeYn.eq(YnType.Y))
                .where(menu.displayYn.eq(YnType.Y))
                .where(role.in(roles))
                .orderBy(menu.sortOrder.asc(), menu.id.asc())
                .fetch();
    }

    public Long getCountMenuData() {
        return this.queryFactory
                .select(menu.count())
                .from(menu)
                .fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return menu.deleteYn.eq(YnType.N);
    }
}
