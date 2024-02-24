package io.gig.realestate.domain.buyer.repository;

import io.gig.realestate.domain.buyer.BuyerReader;
import io.gig.realestate.domain.buyer.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuyerQueryImpl implements BuyerReader {

    private final BuyerQueryRepository queryRepository;

    @Override
    public Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition) {
        return queryRepository.getBuyerPageListBySearch(condition);
    }
}
