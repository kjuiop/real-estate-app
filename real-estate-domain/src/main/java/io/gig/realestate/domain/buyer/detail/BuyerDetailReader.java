package io.gig.realestate.domain.buyer.detail;

import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
public interface BuyerDetailReader {
    Optional<BuyerDetail> getBuyerDetailByBuyerIdAndProcessCd(Long buyerId, Long processCd);

    Optional<ProcessDetailDto> getProcessDetail(Long buyerId, Long processCd);
}
