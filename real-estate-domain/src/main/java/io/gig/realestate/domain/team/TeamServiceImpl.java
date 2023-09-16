package io.gig.realestate.domain.team;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.team.dto.TeamCreateForm;
import io.gig.realestate.domain.team.dto.TeamDto;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import io.gig.realestate.domain.team.types.TeamStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamReader teamReader;
    private final TeamStore teamStore;

    @Override
    @Transactional(readOnly = true)
    public List<TeamListDto> getTeamList() {
        return teamReader.getTeamList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto) {
        return teamReader.getTeamPageListBySearch(searchDto);
    }

    @Override
    @Transactional
    public Long create(TeamCreateForm createForm) {
        Team newTeam = Team.create(createForm);
        return teamStore.store(newTeam).getId();
    }

    @Override
    @Transactional
    public void initTeam(String name, TeamStatus status, Administrator manager) {
        Team team = Team.initTeam(name, status, manager);
        teamStore.store(team);
    }
}
