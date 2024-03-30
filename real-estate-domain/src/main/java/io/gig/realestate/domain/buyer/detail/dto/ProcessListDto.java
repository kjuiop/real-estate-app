package io.gig.realestate.domain.buyer.detail.dto;

import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/02/25
 */
@Getter
public class ProcessListDto {

    private String processName;

    private String processCd;

    private LocalDateTime createdAt;

    private String createdByName;

    public ProcessListDto(BuyerDetail b) {
    }
}
