package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.types.AdminStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-08-26
 */
@Getter
@Setter
public class AdministratorUpdateForm {

    private Long adminId;

    @NotEmpty(message = "이메일을 입력해주세요.")
    private String username;

    private String password;

    private String confirmPassword;

    private AdminStatus status;

    @NotEmpty(message = "역할을 활성화해주세요.")
    private List<String> roleNames;
}
