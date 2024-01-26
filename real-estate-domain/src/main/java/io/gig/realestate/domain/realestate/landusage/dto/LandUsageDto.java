package io.gig.realestate.domain.realestate.landusage.dto;

import io.gig.realestate.domain.realestate.landusage.LandUsageInfo;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/01/23
 */
@Getter
@SuperBuilder
public class LandUsageDto {

    private Long landUsageId;

    private String pnu;

    private String prposAreaDstrcNmList;

    private String prposAreaDstrcCodeList;

    private String posList;

    private int responseCode;

    private LocalDateTime lastCurlApiAt;

    public LandUsageDto(LandUsageInfo u) {
        this.landUsageId = u.getId();
        this.pnu = u.getPnu();
        this.prposAreaDstrcCodeList = u.getPrposAreaDstrcCodeList();
        this.prposAreaDstrcNmList = u.getPrposAreaDstrcNmList();
        this.responseCode = u.getResponseCode();
        this.lastCurlApiAt = u.getLastCurlApiAt();
    }
}
