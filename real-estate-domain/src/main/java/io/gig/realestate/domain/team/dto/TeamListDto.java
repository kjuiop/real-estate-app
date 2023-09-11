package io.gig.realestate.domain.team.dto;

import io.gig.realestate.domain.team.Team;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
public class TeamListDto extends TeamDto {

    public String createdByUsername;
    public String managerUsername;
    public String managerName;

    public TeamListDto(Team t) {
        super(t);
        if (t.getCreatedBy() != null) {
            this.createdByUsername = t.getCreatedBy().getUsername();
        }
        if (t.getManager() != null) {
            this.managerUsername = t.getManager().getUsername();
            this.managerName = t.getManager().getName();
        }
    }
}
