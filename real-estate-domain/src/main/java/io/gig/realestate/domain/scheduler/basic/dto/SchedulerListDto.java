package io.gig.realestate.domain.scheduler.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import io.gig.realestate.domain.scheduler.manager.dto.SchedulerManagerDto;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
public class SchedulerListDto extends SchedulerDto {

    @Builder.Default
    public List<SchedulerManagerDto> managers = new ArrayList<>();

    public SchedulerListDto(Scheduler s) {
        super(s);
        List<SchedulerManagerDto> list = new ArrayList<>();
        for (SchedulerManager sm : s.getManagers()) {
            if (sm.getDeleteYn() == YnType.N && sm.getAdmin().isNormal()) {
                list.add(new SchedulerManagerDto(sm));
            }
        }
        this.managers = list;
    }
}
