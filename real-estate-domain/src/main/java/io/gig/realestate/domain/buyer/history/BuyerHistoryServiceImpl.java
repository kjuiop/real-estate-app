package io.gig.realestate.domain.buyer.history;

import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import io.gig.realestate.domain.buyer.realestate.HistoryRealEstateService;
import io.gig.realestate.domain.buyer.realestate.dto.HistoryRealEstateDto;
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
    private final HistoryRealEstateService historyRealEstateService;

    @Override
    @Transactional(readOnly = true)
    public List<HistoryListDto> getHistoriesByBuyerId(Long buyerId) {
        List<HistoryListDto> list = historyReader.getHistoriesByBuyerId(buyerId);
        for (HistoryListDto dto : list) {
            List<HistoryRealEstateDto> realEstates = historyRealEstateService.getHistoryRealEstateByHistoryId(dto.getHistoryId());
            dto.setRealEstateList(realEstates);
        }
        return list;
    }
}
