package io.gig.realestate.domain.admin.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.admin.QAdministratorRole.administratorRole;
import static io.gig.realestate.domain.admin.QAdministrator.administrator;
import static io.gig.realestate.domain.category.QCategory.category;
import static io.gig.realestate.domain.team.QTeam.team;

/**
 * @author : JAKE
 * @date : 2023/03/18
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministratorQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<AdministratorListDto> getAdminPageListBySearch(AdminSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();

        JPAQuery<AdministratorListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdministratorListDto.class,
                            administrator
                        ))
                .from(administrator)
                .where(where)
                .where(defaultCondition())
                .orderBy(administrator.id.desc())
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        List<AdministratorListDto> content = contentQuery.fetch();
        long total = content.size();

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }


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
                .join(administrator.team, team).fetchJoin()
                .where(administrator.username.eq(username))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    public boolean existByUsername(String username) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(administrator)
                .where(administrator.username.eq(username))
                .fetchFirst();

        return fetchOne != null;
    }

    public Optional<AdministratorDetailDto> getDetailDto(Long adminId) {

        Optional<AdministratorDetailDto> fetch = Optional.ofNullable(this.queryFactory
                .select(Projections.constructor(AdministratorDetailDto.class,
                        administrator))
                .from(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(eqAdminId(adminId))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    public List<AdministratorListDto> getCandidateManagers(AdminSearchDto searchDto, String loginUsername) {

        BooleanBuilder where = new BooleanBuilder();

        JPAQuery<AdministratorListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdministratorListDto.class,
                        administrator))
                .from(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(where)
                .where(defaultCondition())
                .where(neLoginUsername(loginUsername))
                .where(administratorRole.role.name.eq("ROLE_MANAGER"))
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        return contentQuery.fetch();
    }

    public Optional<Administrator> getAdminById(Long adminId) {

        return Optional.ofNullable(this.queryFactory
                .selectFrom(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(defaultCondition())
                .where(administrator.id.eq(adminId))
                .limit(1)
                .fetchFirst());
    }

    public Page<AdministratorListDto> getCandidateMembers(AdminSearchDto searchDto, String loginUsername) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(administratorRole.role.name.ne("ROLE_SUPER_ADMIN"));


        JPAQuery<AdministratorListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdministratorListDto.class,
                        administrator))
                .from(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(where)
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        List<AdministratorListDto> content = contentQuery.fetch();

        Long total = queryFactory
                .select(administrator.count())
                .from(administrator)
                .join(administrator.administratorRoles, administratorRole)
                .where(where)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }

    public List<AdministratorListDto> getAllAdministrators() {
        return this.queryFactory
                .selectDistinct(Projections.constructor(AdministratorListDto.class,
                        administrator))
                .from(administrator)
                .where(defaultCondition())
                .where(administrator.status.eq(AdminStatus.NORMAL))
                .where(administrator.team.isNotNull())
                .orderBy(administrator.id.desc())
                .fetch();
    }

    public List<AdministratorListDto> getAdministratorsByTeam(Team team) {
        return this.queryFactory
                .selectDistinct(Projections.constructor(AdministratorListDto.class,
                        administrator))
                .from(administrator)
                .where(defaultCondition())
                .where(administrator.status.eq(AdminStatus.NORMAL))
                .where(administrator.team.eq(team))
                .orderBy(administrator.id.desc())
                .fetch();
    }

    public Page<AdministratorListDto> getAdminByTeamId(AdminSearchDto searchDto, Long teamId) {
        BooleanBuilder where = new BooleanBuilder();

        JPAQuery<AdministratorListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdministratorListDto.class,
                        administrator))
                .from(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .where(where)
                .where(defaultCondition())
                .where(administratorRole.role.name.eq("ROLE_MEMBER"))
                .where(eqTeamId(teamId))
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        List<AdministratorListDto> content = contentQuery.fetch();
        long total = content.size();

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }

    private BooleanExpression eqTeamId(Long teamId) {
        return teamId != null ? administrator.team.id.eq(teamId) : null;
    }

    private BooleanExpression eqAdminId(Long adminId) {
        return adminId != null ? administrator.id.eq(adminId) : null;
    }

    private BooleanExpression neLoginUsername(String loginUsername) {
        return StringUtils.hasText(loginUsername) ? administrator.username.ne(loginUsername) : null;
    }

    private BooleanExpression defaultCondition() {
        return administrator.deleteYn.eq(YnType.N);
    }

}
