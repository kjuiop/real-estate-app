package io.gig.realestate.domain.team.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Getter
@Setter
public class TeamUpdateForm {

    private Long teamId;

    @NotEmpty(message = "팀 이름을 입력해주세요.")
    private String name;

    private YnType activeYn;
}
