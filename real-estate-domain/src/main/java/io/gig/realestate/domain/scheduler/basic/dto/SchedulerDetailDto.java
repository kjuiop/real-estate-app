package io.gig.realestate.domain.scheduler.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.scheduler.basic.Scheduler;
import io.gig.realestate.domain.scheduler.manager.SchedulerManager;
import io.gig.realestate.domain.scheduler.manager.dto.SchedulerManagerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/15
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerDetailDto extends SchedulerDto {

    private static final SchedulerDetailDto EMPTY;

    public String buyerGradeName;

    @Builder.Default
    public List<SchedulerManagerDto> managers = new ArrayList<>();

    @Builder.Default
    private boolean empty = false;

    static {
        EMPTY = SchedulerDetailDto.builder()
                .empty(true)
                .build();
    }

    public static SchedulerDetailDto emptyDto() {
        return EMPTY;
    }

    public SchedulerDetailDto(Scheduler s) {
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
