package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
import io.gig.realestate.domain.buyer.detail.dto.ProcessListDto;
import io.gig.realestate.domain.common.YnType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/02/18
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDetailDto extends BuyerDto {

    private static final BuyerDetailDto EMPTY;

    @Builder.Default
    public List<ProcessListDto> processList = new ArrayList<>();

    @Builder.Default
    private boolean empty = false;

    static {
        EMPTY = BuyerDetailDto.builder()
                .empty(true)
                .companyScale(CompanyScaleType.Large)
                .companyEstablishAtYn(YnType.N)
                .build();
    }

    public static BuyerDetailDto emptyDto() {
        return EMPTY;
    }

    public BuyerDetailDto(Buyer b) {
        super(b);
    }
}
