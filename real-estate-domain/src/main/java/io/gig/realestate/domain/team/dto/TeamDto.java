package io.gig.realestate.domain.team.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.Team;
import io.gig.realestate.domain.team.types.TeamStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class TeamDto {

    private Long teamId;

    private String name;

    private TeamStatus status;

    public TeamDto(Team t) {
        this.teamId = t.getId();
        this.status = t.getStatus();
        this.name = t.getName();
    }
}
