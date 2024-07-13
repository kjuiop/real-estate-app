package io.gig.realestate.domain.realestate.revenue.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/07/10
 */
@SuperBuilder
@Getter
public class RevenueDetailDto extends RevenueDto {

    private static final RevenueDetailDto EMPTY;

    @Builder.Default
    private boolean empty = false;

    static {
        EMPTY = RevenueDetailDto.builder()
                .empty(true)
                .build();
    }

    public static RevenueDetailDto emptyDto(Long realEstateId) {
        return RevenueDetailDto.builder()
                .realEstateId(realEstateId)
                .build();
    }

}
