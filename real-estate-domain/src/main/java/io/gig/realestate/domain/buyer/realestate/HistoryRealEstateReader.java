package io.gig.realestate.domain.buyer.realestate;

import io.gig.realestate.domain.buyer.realestate.dto.HistoryRealEstateDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/10
 */
public interface HistoryRealEstateReader {
    List<HistoryRealEstateDto> getHistoryRealEstateByHistoryId(Long historyId);
}
