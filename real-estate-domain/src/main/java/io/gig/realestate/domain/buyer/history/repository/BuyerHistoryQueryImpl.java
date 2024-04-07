package io.gig.realestate.domain.buyer.history.repository;

import io.gig.realestate.domain.buyer.history.BuyerHistoryReader;
import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuyerHistoryQueryImpl implements BuyerHistoryReader {

    private final BuyerHistoryQueryRepository queryRepository;

    @Override
    public List<HistoryListDto> getHistoriesByBuyerId(Long buyerId) {
        return queryRepository.getHistoriesByBuyerId(buyerId);
    }
}
