package io.gig.realestate.domain.buyer.maps.repository;

import io.gig.realestate.domain.buyer.maps.BuyerHistoryMapReader;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;
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
public class BuyerHistoryMapQueryImpl implements BuyerHistoryMapReader {

    private final BuyerHistoryMapQueryRepository queryRepository;

    @Override
    public List<HistoryMapListDto> getHistoryMapByBuyerId(Long buyerId) {
        return queryRepository.getHistoriesByBuyerId(buyerId);
    }
}
