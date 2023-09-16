package io.gig.realestate.domain.team.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Getter
@Setter
public class TeamCreateForm {

    @NotEmpty(message = "팀 이름을 입력해주세요.")
    private String name;
}
