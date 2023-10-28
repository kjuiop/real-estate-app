package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.dto.AdministratorCreateForm;
import io.gig.realestate.domain.admin.dto.AdministratorSignUpForm;
import io.gig.realestate.domain.admin.dto.AdministratorTemUpdateForm;
import io.gig.realestate.domain.admin.dto.AdministratorUpdateForm;
import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.role.Role;
import io.gig.realestate.domain.team.Team;
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

    private String phone;

    @Builder.Default
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private AdminStatus status = AdminStatus.PENDING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    private String emailValidCode;

    private LocalDateTime emailValidatedAt;

    private LocalDateTime lastLoginAt;

    private LocalDateTime withDrawAt;

    @Builder.Default
    @Column(columnDefinition = "integer default 0")
    private Integer passwordFailureCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

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

    public static Administrator signUp(AdministratorSignUpForm signUpForm, String encodedPassword, Team team) {
        return Administrator.builder()
                .username(signUpForm.getUsername())
                .name(signUpForm.getName())
                .password(encodedPassword)
                .passwordFailureCount(0)
                .phone(signUpForm.getPhone())
                .team(team)
                .build();
    }

    public void createAdministratorRoles(List<Role> roles) {
        roles.stream().map(role -> AdministratorRole.addAdministratorRole(this, role))
                .forEach(administratorRole -> this.getAdministratorRoles().add(administratorRole));
    }

    public static Administrator initAdministrator(String username, String password, String name, String phone) {
        return Administrator.builder()
                .username(username)
                .password(password)
                .name(name)
                .phone(phone)
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

    public void addTeam(Team team) {
        this.team = team;
    }

    public void update(AdministratorUpdateForm form, String encodedPassword) {
        this.password = encodedPassword;
        this.status = form.getStatus();
    }

    public void updateStatus(AdminStatus status) {
        this.status = status;
    }

    public void updateAdministratorRoles(List<Role> roles) {
        this.administratorRoles.clear();
        roles.stream().map(role -> AdministratorRole.addAdministratorRole(this, role))
                .forEach(administratorRole -> this.getAdministratorRoles().add(administratorRole));
    }

    public boolean passwordValid(String inputPassword) {
        return this.getPassword().equals(inputPassword);
    }

    public void updateAdminStatus(AdministratorTemUpdateForm form) {
        this.status = form.getStatus();
    }
}
