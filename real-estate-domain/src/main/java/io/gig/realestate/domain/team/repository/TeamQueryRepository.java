package io.gig.realestate.domain.team.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.Team;
import io.gig.realestate.domain.team.dto.TeamDetailDto;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static io.gig.realestate.domain.team.QTeam.team;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();

        JPAQuery<TeamListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(TeamListDto.class,
                        team))
                .from(team)
                .where(where)
                .where(defaultCondition())
                .where(likeName(searchDto.getName()))
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        List<TeamListDto> content = contentQuery.fetch();
        long total = content.size();

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }

    public List<TeamListDto> getTeamList() {
        return this.queryFactory
                .selectDistinct(Projections.constructor(TeamListDto.class, team))
                .from(team)
                .where(defaultCondition())
                .orderBy(team.id.asc())
                .fetch();
    }

    public Optional<Team> getTeamById(Long teamId) {
        return Optional.ofNullable(
                this.queryFactory
                        .selectFrom(team)
                        .where(defaultCondition())
                        .where(team.id.eq(teamId))
                        .fetchOne());
    }

    public Optional<TeamDetailDto> getTeamDetail(Long teamId) {

        TeamDetailDto teamDetailDto = queryFactory
                .select(Projections.constructor(TeamDetailDto.class,
                        team
                ))
                .from(team)
                .where(defaultCondition())
                .where(eqTeamId(teamId))
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(teamDetailDto);
    }

    private BooleanExpression defaultCondition() {
        return team.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqTeamId(Long teamId) {
        return teamId != null ? team.id.eq(teamId) : null;
    }

    private BooleanExpression likeName(String name) {
        return StringUtils.hasText(name) ? team.name.like("%" + name + "%") : null;
    }
}
