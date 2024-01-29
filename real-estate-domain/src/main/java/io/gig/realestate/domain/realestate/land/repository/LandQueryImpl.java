package io.gig.realestate.domain.realestate.land.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.land.LandReader;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LandQueryImpl implements LandReader {

    private final LandQueryRepository queryRepository;

    @Override
    public List<LandListDto> getLandListDtoByRealEstateId(Long realEstateId) {
        return queryRepository.getLandListDtoByRealEstateId(realEstateId);
    }

    @Override
    public LandInfo getLandInfoByLandId(Long landId) {
        Optional<LandInfo> findLandInfo = queryRepository.getLandInfoByLandId(landId);
        if (findLandInfo.isEmpty()) {
            throw new NotFoundException(landId + "의 토지 정보가 없습니다.");
        }
        return findLandInfo.get();
    }


}
