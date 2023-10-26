package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.types.AdminStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/10/24
 */
@Getter
@Setter
public class AdministratorTemUpdateForm {

    private Long adminId;

    private AdminStatus status;

    private String role;
}
