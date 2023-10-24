package io.gig.realestate.domain.team;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.dto.*;
import io.gig.realestate.domain.team.types.TeamStatus;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
public interface TeamService {

    Long create(TeamCreateForm createForm, LoginUser loginUser);

    Team getTeamById(Long teamId);

    Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto);

    void initTeam(String name, YnType activeYn, Administrator manager);

    List<TeamListDto> getTeamList();

    TeamDetailDto getDetail(Long teamId);
}
