package io.gig.realestate.domain.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.gig.realestate.domain.admin.QAdministratorRole.administratorRole;
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

    public Optional<AdministratorDetailDto> getAdminByUsername(String username) {
        Optional<AdministratorDetailDto> fetch = Optional.ofNullable(this.queryFactory
                .select(Projections.constructor(AdministratorDetailDto.class, administrator))
                .from(administrator)
                        .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(administrator.username.eq(username))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    public Long getCountAdministrators() {
        return this.queryFactory
                .select(administrator.count())
                .from(administrator)
                .fetchOne();
    }

    public Optional<Administrator> getAdministratorEntityByUsername(String username) {
        return Optional.ofNullable(
                this.queryFactory
                        .selectFrom(administrator)
                        .where(administrator.username.eq(username))
                        .fetchOne());
    }

    public Optional<Administrator> getAdminEntityByUsername(String username) {
        Optional<Administrator> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(administrator.username.eq(username))
                .limit(1)
                .fetchFirst());

        return fetch;
    }
}
