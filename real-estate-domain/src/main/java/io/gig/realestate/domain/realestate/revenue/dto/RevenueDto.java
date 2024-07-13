package io.gig.realestate.domain.realestate.revenue.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/07/10
 */
@SuperBuilder
@Getter
public class RevenueDto {

    private Long revenueId;

    private Long realEstateId;
}
