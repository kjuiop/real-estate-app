package io.gig.realestate.domain.team;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.dto.*;
import io.gig.realestate.domain.team.types.TeamStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    public List<TeamListDto> getTeamListByLoginUser(LoginUser loginUser) {
        if (loginUser.isSuperAdmin()) {
            return teamReader.getTeamList();
        }
        Team team = teamReader.getTeamById(loginUser.getTeamId());
        return List.of(new TeamListDto(team));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto) {
        return teamReader.getTeamPageListBySearch(searchDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Team getTeamById(Long teamId) {
        return teamReader.getTeamById(teamId);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDetailDto getDetail(Long teamId) {
        return teamReader.getTeamDetail(teamId);
    }

    @Override
    @Transactional
    public Long create(TeamCreateForm createForm, LoginUser loginUser) {
        Team newTeam = Team.create(createForm, loginUser.getLoginUser());
        return teamStore.store(newTeam).getId();
    }

    @Override
    @Transactional
    public Long update(TeamUpdateForm updateForm, LoginUser loginUser) {
        Team team = teamReader.getTeamById(updateForm.getTeamId());
        team.update(updateForm, loginUser.getLoginUser());
        return team.getId();
    }

    @Override
    @Transactional
    public void initTeam(String name, YnType activeYn, Administrator manager) {
        Team team = Team.initTeam(name, activeYn, manager);
        manager.addTeam(team);
        teamStore.store(team);
    }
}
