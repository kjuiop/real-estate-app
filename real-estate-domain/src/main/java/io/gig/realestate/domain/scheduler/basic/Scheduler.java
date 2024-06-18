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

    @Lob
    private String buyerGradeCds;

    private String customerName;

    private String memo;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String colorCode;

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

    public static Scheduler create(SchedulerForm createForm, String colorCode, Administrator loginUser) {
        return Scheduler.builder()
                .buyerGradeCds(createForm.getBuyerGradeCds())
                .title(createForm.getTitle())
                .customerName(createForm.getCustomerName())
                .memo(createForm.getMemo())
                .startDate(createForm.getStartDate())
                .endDate(createForm.getEndDate())
                .colorCode(colorCode)
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
    }

    public void update(SchedulerForm updateForm, String colorCode, Administrator loginAdmin) {
        this.title = updateForm.getTitle();
        this.buyerGradeCds = updateForm.getBuyerGradeCds();
        this.customerName = updateForm.getCustomerName();
        this.memo = updateForm.getMemo();
        this.colorCode = colorCode;
        this.updatedBy = loginAdmin;
    }

    public void delete(Administrator loginUser) {
        this.deleteYn = YnType.Y;
        this.updatedBy = loginUser;
    }
}
