package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
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
    private Long managerId;

    static {
        EMPTY = RealEstateDetailDto.builder()
                .ownYn(YnType.Y)
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
        if (r.getManager() != null) {
            this.managerId = r.getManager().getId();
        }
    }

    public static RealEstateDetailDto initDetailDto(String address, String bCode, String hCode) {
        return RealEstateDetailDto.builder()
                .address(address)
                .bCode(bCode)
                .hCode(hCode)
                .ownYn(YnType.Y)
                .build();
    }
}
