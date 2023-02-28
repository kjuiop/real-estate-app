package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.common.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
