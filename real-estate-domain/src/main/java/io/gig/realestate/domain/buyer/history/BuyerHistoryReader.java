package io.gig.realestate.domain.buyer.history;

import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
public interface BuyerHistoryReader {
    List<HistoryListDto> getHistoriesByBuyerId(Long buyerId);
}
