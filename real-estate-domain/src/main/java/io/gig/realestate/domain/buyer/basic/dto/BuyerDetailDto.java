package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
import io.gig.realestate.domain.buyer.detail.dto.ProcessListDto;
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

    public ProcessDetailDto processDetailDto;

    @Builder.Default
    public List<ProcessListDto> processList = new ArrayList<>();

    @Builder.Default
    private boolean empty = false;

    static {
        EMPTY = BuyerDetailDto.builder()
                .empty(true)
                .processDetailDto(new ProcessDetailDto())
                .build();
    }

    public static BuyerDetailDto emptyDto() {
        return EMPTY;
    }

    public BuyerDetailDto(Buyer b) {
        super(b);
//        if (b.getBuyerDetails().size() > 0) {
//            List<ProcessListDto> list = new ArrayList<>();
//            for (int i=0; i<b.getBuyerDetails().size(); i++) {
//                list.add(new ProcessListDto(b.getBuyerDetails().get(i)));
//                if (i == b.getBuyerDetails().size()-1) {
//                    this.processDetailDto = new ProcessDetailDto(b.getBuyerDetails().get(i));
//                }
//            }
//            this.processList = list;
//        }
    }
}
