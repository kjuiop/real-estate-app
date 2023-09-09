package io.gig.realestate.domain.team;

import io.gig.realestate.domain.team.dto.TeamCreateForm;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
public interface TeamService {

    Long create(TeamCreateForm createForm);
}
