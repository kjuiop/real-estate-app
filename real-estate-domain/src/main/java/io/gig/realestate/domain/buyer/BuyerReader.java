package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.buyer.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerReader {
    Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition);

    BuyerDetailDto getBuyerDetail(Long buyerId);

    Optional<BuyerDetail> getBuyerDetailByIdAndProcessCd(Long buyerId, Long processCd);

    Buyer getBuyerById(Long buyerId);
}
