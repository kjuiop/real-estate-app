package io.gig.realestate.domain.buyer.maps.dto;

import io.gig.realestate.domain.buyer.maps.BuyerHistoryMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class HistoryMapDto {

    private Long mapId;

    private String processCds;

    private String processName;

    private String colorCode;

    private int historyCnt;

    public HistoryMapDto(BuyerHistoryMap hm) {
        this.mapId = hm.getId();
        this.processCds = hm.getProcessCds();
        this.processName = hm.getProcessName();
        this.historyCnt = hm.getHistoryCnt();
        this.colorCode = hm.getColorCode();
    }
}
