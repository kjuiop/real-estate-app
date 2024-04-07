package io.gig.realestate.domain.buyer.history;

import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuyerHistoryServiceImpl implements BuyerHistoryService {

    private final BuyerHistoryReader historyReader;

    @Override
    @Transactional(readOnly = true)
    public List<HistoryListDto> getHistoriesByBuyerId(Long buyerId) {
        return historyReader.getHistoriesByBuyerId(buyerId);
    }
}
