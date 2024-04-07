package io.gig.realestate.domain.buyer.basic.repository;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.basic.BuyerReader;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
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
    public Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition, Administrator loginUser) {
        return queryRepository.getBuyerPageListBySearch(condition, loginUser);
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
}
