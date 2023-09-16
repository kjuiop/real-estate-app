package io.gig.realestate.domain.team.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.team.Team;
import io.gig.realestate.domain.team.TeamReader;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamQueryImpl implements TeamReader {

    private final TeamQueryRepository teamQueryRepository;

    @Override
    public Team getTeamById(Long teamId) {
        Optional<Team> findTeam = teamQueryRepository.getTeamById(teamId);
        if (findTeam.isEmpty()) {
            throw new NotFoundException(teamId + " 팀은 없습니다.");
        }

        return findTeam.get();
    }

    @Override
    public List<TeamListDto> getTeamList() {
        return teamQueryRepository.getTeamList();
    }

    @Override
    public Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto) {
        return teamQueryRepository.getTeamPageListBySearch(searchDto);
    }
}
