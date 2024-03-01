package io.gig.realestate.domain.buyer.detail;

import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
public interface BuyerDetailService {

    Optional<BuyerDetail> getBuyerDetailByIdAndProcessCd(Long buyerId, Long processCd);

    ProcessDetailDto getBuyerDetailByProcessCd(Long buyerId, Long processCd);

}
