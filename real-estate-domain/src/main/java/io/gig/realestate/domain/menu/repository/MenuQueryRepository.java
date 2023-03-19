package io.gig.realestate.domain.menu.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static io.gig.realestate.domain.menu.QMenu.menu;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Long getCountMenuData() {
        return this.queryFactory
                .select(menu.count())
                .from(menu)
                .fetchOne();
    }

}
