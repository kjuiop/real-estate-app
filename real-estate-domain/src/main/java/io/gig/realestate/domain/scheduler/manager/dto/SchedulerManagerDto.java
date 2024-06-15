package io.gig.realestate.domain.scheduler.manager.dto;

import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/06/15
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class SchedulerManagerDto {

    private Long schedulerManagerId;

    private String username;

    private String name;

    private Long adminId;

    public SchedulerManagerDto(SchedulerManager s) {
        this.schedulerManagerId = s.getId();
        this.username = s.getUsername();
        this.name = s.getName();
        this.adminId = s.getAdmin().getId();
    }
}
