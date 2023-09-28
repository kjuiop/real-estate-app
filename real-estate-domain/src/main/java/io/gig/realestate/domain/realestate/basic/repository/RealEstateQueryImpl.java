package io.gig.realestate.domain.realestate.basic.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto) {
        return queryRepository.getRealEstatePageListBySearch(searchDto);
    }

    @Override
    public RealEstateDetailDto getRealEstateDetail(Long realEstateId) {

        Optional<RealEstateDetailDto> findDetail = queryRepository.getRealEstateDetail(realEstateId);
        if (findDetail.isEmpty()) {
            throw new NotFoundException(realEstateId + "의 매물관리 데이터가 없습니다.");
        }

        return findDetail.get();
    }
}
