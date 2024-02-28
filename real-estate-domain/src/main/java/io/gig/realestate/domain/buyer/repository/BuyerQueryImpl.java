package io.gig.realestate.domain.buyer.repository;

import io.gig.realestate.domain.buyer.Buyer;
import io.gig.realestate.domain.buyer.BuyerDetail;
import io.gig.realestate.domain.buyer.BuyerReader;
import io.gig.realestate.domain.buyer.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import io.gig.realestate.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public Buyer getBuyerById(Long buyerId) {
        Optional<Buyer> findBuyer = queryRepository.getBuyerById(buyerId);
        if (findBuyer.isEmpty()) {
            throw new NotFoundException(buyerId + "의 정보가 없습니다.");
        }

        return findBuyer.get();
    }

    @Override
    public BuyerDetailDto getBuyerDetail(Long buyerId) {

        Optional<BuyerDetailDto> findDetail = queryRepository.getBuyerDetail(buyerId);
        if (findDetail.isEmpty()) {
            throw new NotFoundException(buyerId + "의 정보가 없습니다.");
        }

        return findDetail.get();
    }

    @Override
    public Optional<BuyerDetail> getBuyerDetailByIdAndProcessCd(Long buyerId, Long processCdId) {
        return queryRepository.getBuyerDetailByIdAndProcessCd(buyerId, processCdId);
    }
}
