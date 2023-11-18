package io.gig.realestate.domain.realestate.basic.repository;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailAllDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RealEstateQueryImpl implements RealEstateReader {

    private final RealEstateQueryRepository queryRepository;

    @Override
    public RealEstate getRealEstateById(Long realEstateId) {

        Optional<RealEstate> findRealEstate = queryRepository.getRealEstateById(realEstateId);
        if (findRealEstate.isEmpty()) {
            throw new NotFoundException(realEstateId + "의 매물 데이터가 없습니다.");
        }

        return findRealEstate.get();
    }

    @Override
    public Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto) {
        return queryRepository.getRealEstatePageListBySearch(searchDto);
    }

    @Override
    public RealEstateDetailDto getRealEstateDetail(Long realEstateId) {

        Optional<RealEstateDetailDto> findDetail = queryRepository.getRealEstateDetail(realEstateId);
        if (findDetail.isEmpty()) {
            throw new NotFoundException(realEstateId + "의 매물 데이터가 없습니다.");
        }

        return findDetail.get();
    }

    @Override
    public boolean isExistAddress(String address) {
        Long count = queryRepository.isExistAddress(address);
        return count > 0;
    }

    @Override
    public Long getPrevRealEstateId(Long realEstateId) {
        Optional<RealEstate> prevRealEstate = queryRepository.getPrevRealEstateId(realEstateId);
        if (prevRealEstate.isEmpty()) {
            return null;
        }

        return prevRealEstate.get().getId();
    }

    @Override
    public Long getNextRealEstateId(Long realEstateId) {
        Optional<RealEstate> nextRealEstate = queryRepository.getNextRealEstateId(realEstateId);
        if (nextRealEstate.isEmpty()) {
            return null;
        }

        return nextRealEstate.get().getId();
    }

    @Override
    public List<Long> getRealEstateIdsBySearch(RealEstateSearchDto searchDto) {
        return queryRepository.getRealEstateIdsBySearch(searchDto);
    }

    @Override
    public RealEstateDetailAllDto getRealEstateDetailAllInfo(Long realEstateId) {
        Optional<RealEstateDetailAllDto> findRealEstate = queryRepository.getRealEstateAllInfo(realEstateId);
        if (findRealEstate.isEmpty()) {
            throw new NotFoundException(realEstateId + "의 매물 데이터가 없습니다.");
        }

        return findRealEstate.get();
    }
}
