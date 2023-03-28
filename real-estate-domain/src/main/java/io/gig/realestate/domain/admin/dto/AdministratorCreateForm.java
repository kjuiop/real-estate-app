package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.types.AdminStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/26
 */
@Getter
@Setter
public class AdministratorCreateForm {

    @Email(message = "올바른 Email을 입력해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String username;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "패스워드를 입력해주세요.")
    private String password;

    @NotEmpty(message = "패스워드를 확인해주세요.")
    private String confirmPassword;

    private AdminStatus status;

    @NotEmpty(message = "역할을 활성화해주세요.")
    private List<String> roleNames;
}
