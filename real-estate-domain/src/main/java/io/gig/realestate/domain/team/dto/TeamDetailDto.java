package io.gig.realestate.domain.team.dto;

import io.gig.realestate.domain.team.Team;
import io.gig.realestate.domain.team.types.TeamStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/09/11
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDetailDto extends TeamDto {

    private static final TeamDetailDto EMPTY;

    static {
        EMPTY = TeamDetailDto.builder()
                .empty(true)
                .status(TeamStatus.INACTIVE)
                .build();
    }

    @Builder.Default
    private boolean empty = false;

    public static TeamDetailDto emptyDto() {
        return EMPTY;
    }

    public TeamDetailDto(Team t) {
        super(t);
    }
}
