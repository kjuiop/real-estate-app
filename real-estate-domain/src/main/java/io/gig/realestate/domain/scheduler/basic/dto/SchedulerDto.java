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

    private String customerName;

    private String memo;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public SchedulerDto(Scheduler s) {
        this.schedulerId = s.getId();
        this.title = s.getTitle();
        this.buyerGradeCds = s.getBuyerGradeCds();
        this.customerName = s.getCustomerName();
        this.memo = s.getMemo();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
    }
}
