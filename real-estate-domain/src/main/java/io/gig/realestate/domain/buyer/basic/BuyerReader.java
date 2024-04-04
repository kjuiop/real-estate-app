package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerReader {
    Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition);

    BuyerDetailDto getBuyerDetail(Long buyerId);

    Buyer getBuyerById(Long buyerId);
}
