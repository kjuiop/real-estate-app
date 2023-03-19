package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.types.AdminStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/03/18
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class AdministratorDto {

    private Long adminId;

    private String username;

    private String password;

    private String name;

    private AdminStatus status;

    private Integer passwordFailureCount;

    private boolean isNormal;

    private boolean isValidEmailAuth;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AdministratorDto(Administrator a) {
        this.adminId = a.getId();
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.name = a.getName();
        this.passwordFailureCount = a.getPasswordFailureCount();
        this.status = a.getStatus();
        this.isNormal = a.isNormal();
        this.isValidEmailAuth = a.isValidEmailAuth();
        this.lastLoginAt = a.getLastLoginAt();
        this.createdAt = a.getCreatedAt();
        this.updatedAt = a.getUpdatedAt();
    }

}
