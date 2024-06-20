package io.gig.realestate.domain.scheduler.basic.dto;

import io.gig.realestate.domain.scheduler.basic.Scheduler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class SchedulerDto {

    private Long schedulerId;

    private String title;

    private String buyerGradeCds;

    private String priorityOrderCds;

    private String customerName;

    private String memo;

    private String colorCode;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long buyerId;

    public SchedulerDto(Scheduler s) {
        this.schedulerId = s.getId();
        this.title = s.getTitle();
        this.priorityOrderCds = s.getPriorityOrderCds();
        this.customerName = s.getCustomerName();
        this.memo = s.getMemo();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
        this.colorCode = s.getColorCode();
        if (s.getBuyer() != null) {
            this.buyerId = s.getBuyer().getId();
        }
    }
}
