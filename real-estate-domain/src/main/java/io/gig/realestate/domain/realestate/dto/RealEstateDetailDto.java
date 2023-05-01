package io.gig.realestate.domain.realestate.dto;

import io.gig.realestate.domain.realestate.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateDetailDto extends RealEstateDto {

    private static final RealEstateDetailDto EMPTY;

    static {
        EMPTY = RealEstateDetailDto.builder()
                .empty(true)
                .build();
    }

    @Builder.Default
    private boolean empty = false;

    public static RealEstateDetailDto emptyDto() {
        return EMPTY;
    }

    public RealEstateDetailDto(RealEstate r) {
        super(r);
    }
}
