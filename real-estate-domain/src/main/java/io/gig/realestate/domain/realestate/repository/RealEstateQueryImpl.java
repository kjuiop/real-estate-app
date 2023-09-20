package io.gig.realestate.domain.realestate.repository;

import io.gig.realestate.domain.realestate.RealEstateReader;
import io.gig.realestate.domain.realestate.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.dto.RealEstateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
