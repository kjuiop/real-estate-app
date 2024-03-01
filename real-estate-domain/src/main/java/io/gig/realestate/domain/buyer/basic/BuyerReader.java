package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.detail.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
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

    Optional<ProcessDetailDto> getProcessDetail(Long buyerId, Long processCd);
}
