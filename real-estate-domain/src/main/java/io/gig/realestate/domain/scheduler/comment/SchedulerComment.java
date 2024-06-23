package io.gig.realestate.domain.scheduler.comment;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.comment.dto.SchedulerCommentForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SchedulerComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @Column(length = 1000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private SchedulerComment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<SchedulerComment> child = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static SchedulerComment create(SchedulerCommentForm createForm, Scheduler scheduler, Administrator loginAdmin) {
        return SchedulerComment.builder()
                .comment(createForm.getComment())
                .scheduler(scheduler)
                .createdBy(loginAdmin)
                .updatedBy(loginAdmin)
                .build();
    }
}
