package io.gig.realestate.domain.buyer.history.dto;

import io.gig.realestate.domain.buyer.history.BuyerHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/04/03
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class HistoryDto {

    private Long historyId;

    private String memo;

    private String processCds;

    private String processName;

    private LocalDateTime createdAt;

    private String createdByName;

    public HistoryDto(BuyerHistory h) {
        this.historyId = h.getId();
        this.memo = h.getMemo();
        this.processCds = h.getProcessCds();
        this.processName = h.getProcessName();
        this.createdByName = h.getCreatedBy().getName();
        this.createdAt = h.getCreatedAt();
    }
}
