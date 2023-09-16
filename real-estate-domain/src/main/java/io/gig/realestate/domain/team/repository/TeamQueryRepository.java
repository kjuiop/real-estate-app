package io.gig.realestate.domain.team.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private BooleanExpression defaultCondition() {
        return team.deleteYn.eq(YnType.N);
    }
}
