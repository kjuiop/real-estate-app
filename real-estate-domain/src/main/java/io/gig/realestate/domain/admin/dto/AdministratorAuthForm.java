package io.gig.realestate.domain.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/06/10
 */
@Getter
@Setter
public class AdministratorAuthForm {

    private String username;

    private String authCode;
}
