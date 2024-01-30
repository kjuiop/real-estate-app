package io.gig.realestate.domain.realestate.landusage.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/01/23
 */
@Getter
@Setter
public class LandUsageCreateForm {

    private Long landUsageId;

    private String pnu;

    private String prposAreaDstrcNmList;

    private String prposAreaDstrcCodeList;

    private String posList;

    private int responseCode;

    private LocalDateTime lastCurlApiAt;
}
