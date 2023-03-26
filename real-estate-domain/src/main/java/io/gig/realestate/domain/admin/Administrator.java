package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.dto.AdministratorCreateForm;
import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.role.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Administrator extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    @Builder.Default
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private AdminStatus status = AdminStatus.PENDING;

    private String emailValidCode;

    private LocalDateTime emailValidatedAt;

    private LocalDateTime lastLoginAt;

    private LocalDateTime withDrawAt;

    @Builder.Default
    @Column(columnDefinition = "integer default 0")
    private Integer passwordFailureCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @Builder.Default
    @OneToMany(mappedBy = "administrator", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<AdministratorRole> administratorRoles = new HashSet<>();

    public void addRole(AdministratorRole role) {
        this.administratorRoles.add(role);
    }

    public Set<Role> getRoles() {
        return administratorRoles.stream().map(AdministratorRole::getRole).collect(Collectors.toSet());
    }


    public static Administrator create(AdministratorCreateForm createForm, String encodedPassword) {
        return Administrator.builder()
                .username(createForm.getUsername())
                .name(createForm.getName())
                .password(encodedPassword)
                .passwordFailureCount(0)
                .status(createForm.getStatus())
                .build();
    }

    public void createAdministratorRoles(List<Role> roles) {
        roles.stream().map(role -> AdministratorRole.addAdministratorRole(this, role))
                .forEach(administratorRole -> this.getAdministratorRoles().add(administratorRole));
    }

    public static Administrator initAdministrator(String username, String password, String name) {
        return Administrator.builder()
                .username(username)
                .password(password)
                .name(name)
                .emailValidatedAt(LocalDateTime.now())
                .status(AdminStatus.NORMAL)
                .build();
    }

    public boolean isNormal() {
        return this.status == AdminStatus.NORMAL;
    }

    public void loginSuccess() {
        this.lastLoginAt = LocalDateTime.now();
        this.passwordFailureCount = 0;
    }

    public void increasePasswordFailureCount() {
        this.passwordFailureCount += 1;

        if (this.passwordFailureCount >= 5) {
            this.status = AdminStatus.INACTIVE;
        }
    }

    public boolean isValidEmailAuth() {
        if (this.emailValidatedAt == null) {
            return false;
        }

        return true;
    }
}
