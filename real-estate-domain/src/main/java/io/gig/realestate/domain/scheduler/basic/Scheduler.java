package io.gig.realestate.domain.scheduler.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Scheduler extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    private String title;

    private String customerName;

    private String memo;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @Builder.Default
    @OneToMany(mappedBy = "scheduler", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SchedulerManager> managers = new ArrayList<>();

    public void addManager(SchedulerManager manager) {
        this.managers.add(manager);
    }

    public static Scheduler create(SchedulerForm createForm, Administrator loginUser) {
        return Scheduler.builder()
                .title(createForm.getTitle())
                .customerName(createForm.getCustomerName())
                .memo(createForm.getMemo())
                .startDate(createForm.getStartDate())
                .endDate(createForm.getEndDate())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void update(SchedulerForm updateForm, Administrator loginAdmin) {
        this.title = updateForm.getTitle();
        this.customerName = updateForm.getCustomerName();
        this.memo = updateForm.getMemo();
        this.updatedBy = loginAdmin;
    }
}
