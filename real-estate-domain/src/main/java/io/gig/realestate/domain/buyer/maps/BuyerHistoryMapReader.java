package io.gig.realestate.domain.buyer.maps;

import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/04
 */
public interface BuyerHistoryMapReader {
    List<HistoryMapListDto> getHistoryMapByBuyerId(Long buyerId);
}
