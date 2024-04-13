package io.gig.realestate.domain.buyer.realestate;

import io.gig.realestate.domain.buyer.realestate.dto.HistoryRealEstateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/10
 */
@Service
@RequiredArgsConstructor
public class HistoryRealEstateServiceImpl implements HistoryRealEstateService {

    private final HistoryRealEstateReader historyRealEstateReader;

    @Override
    @Transactional(readOnly = true)
    public List<HistoryRealEstateDto> getHistoryRealEstateByHistoryId(Long historyId) {
        return historyRealEstateReader.getHistoryRealEstateByHistoryId(historyId);
    }
}
