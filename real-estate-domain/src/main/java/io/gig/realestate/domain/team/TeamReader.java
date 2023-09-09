package io.gig.realestate.domain.team;

import io.gig.realestate.domain.team.dto.TeamDto;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
public interface TeamReader {
    Page<TeamListDto> getTeamPageListBySearch(TeamSearchDto searchDto);
}
