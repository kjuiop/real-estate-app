package io.gig.realestate.domain.buyer.maps;

import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;
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
public class BuyerHistoryMapServiceImpl implements BuyerHistoryMapService {

    private final BuyerHistoryMapReader buyerHistoryMapReader;

    @Override
    @Transactional(readOnly = true)
    public List<HistoryMapListDto> getHistoryMapByBuyerId(Long buyerId) {
        return buyerHistoryMapReader.getHistoryMapByBuyerId(buyerId);
    }
}
