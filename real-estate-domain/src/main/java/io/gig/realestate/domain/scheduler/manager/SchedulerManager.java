package io.gig.realestate.domain.scheduler.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/06/15
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SchedulerManager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private Administrator admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static SchedulerManager create(Scheduler scheduler, Administrator manager, Administrator loginUser) {
        return SchedulerManager.builder()
                .scheduler(scheduler)
                .name(manager.getName())
                .username(manager.getUsername())
                .admin(manager)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void update(Scheduler scheduler, Administrator manager, Administrator loginUser) {
        this.updatedBy = loginUser;
        this.name = manager.getName();
        this.username = manager.getUsername();
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }

}
