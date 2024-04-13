package io.gig.realestate.domain.buyer.realestate.repository;

import io.gig.realestate.domain.buyer.realestate.HistoryRealEstateReader;
import io.gig.realestate.domain.buyer.realestate.dto.HistoryRealEstateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/10
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryRealEstateQueryImpl implements HistoryRealEstateReader {

    private final HistoryRealEstateQueryRepository queryRepository;

    @Override
    public List<HistoryRealEstateDto> getHistoryRealEstateByHistoryId(Long historyId) {
        return queryRepository.getHistoryRealEstateByHistoryId(historyId);
    }
}
