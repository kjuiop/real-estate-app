package io.gig.realestate.domain.scheduler.basic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2024/06/16
 */
@Getter
@Setter
@NoArgsConstructor
public class SchedulerSearchDto {

    private Long adminId;

    private Long buyerId;

    private String priorityOrderCds;

    private String processCds;

    private boolean isWithdraw = false;
}
