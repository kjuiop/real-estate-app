package io.gig.realestate.domain.buyer.detail.repository;

import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.detail.BuyerDetailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuyerDetailQueryImpl implements BuyerDetailReader {

    private final BuyerDetailQueryRepository queryRepository;

    @Override
    public Optional<BuyerDetail> getBuyerDetailByBuyerIdAndProcessCd(Long buyerId, Long processCd) {
        return queryRepository.getBuyerDetailByBuyerIdAndProcessCd(buyerId, processCd);
    }
}
