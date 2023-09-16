package io.gig.realestate.domain.team;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.team.dto.TeamCreateForm;
import io.gig.realestate.domain.team.dto.TeamDto;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import io.gig.realestate.domain.team.types.TeamStatus;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
public interface TeamService {

    Long create(TeamCreateForm createForm);

    Team getTeamById(Long teamId);

    Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto);

    void initTeam(String name, TeamStatus status, Administrator manager);

    List<TeamListDto> getTeamList();
}
