package io.gig.realestate.domain.team;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.team.dto.TeamCreateForm;
import io.gig.realestate.domain.team.types.TeamStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TeamStatus status = TeamStatus.ACTIVE;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Administrator manager;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Administrator> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static Team create(TeamCreateForm createForm) {
        return Team.builder()
                .name(createForm.getName())
                .build();
    }

    public static Team initTeam(String name, TeamStatus status, Administrator manager) {
        return Team.builder()
                .name(name)
                .status(status)
                .manager(manager)
                .createdBy(manager)
                .updatedBy(manager)
                .build();
    }
}
