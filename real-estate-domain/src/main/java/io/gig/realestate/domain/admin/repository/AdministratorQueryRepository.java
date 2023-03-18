package io.gig.realestate.domain.admin.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static io.gig.realestate.domain.admin.QAdministrator.administrator;

/**
 * @author : JAKE
 * @date : 2023/03/18
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministratorQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Long getCountAdministrators() {
        return this.queryFactory
                .select(administrator.count())
                .from(administrator)
                .fetchOne();
    }
}
