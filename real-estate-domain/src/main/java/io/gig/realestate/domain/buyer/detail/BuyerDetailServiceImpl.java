package io.gig.realestate.domain.buyer.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2024/03/01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuyerDetailServiceImpl implements BuyerDetailService {

    private final BuyerDetailReader buyerDetailReader;

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyerDetail> getBuyerDetailByIdAndProcessCd(Long buyerId, Long processCd) {
        return buyerDetailReader.getBuyerDetailByBuyerIdAndProcessCd(buyerId, processCd);
    }
}
