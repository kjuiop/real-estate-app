package io.gig.realestate.domain.buyer.detail;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
public interface BuyerDetailService {
    Optional<BuyerDetail> getBuyerDetailByIdAndProcessCd(Long buyerId, Long processCd);
}
