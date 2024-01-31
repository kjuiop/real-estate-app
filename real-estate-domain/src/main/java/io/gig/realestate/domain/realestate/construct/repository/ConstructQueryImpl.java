package io.gig.realestate.domain.realestate.construct.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.realestate.construct.ConstructReader;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorListDto;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConstructQueryImpl implements ConstructReader {

    private final ConstructQueryRepository queryRepository;

    @Override
    public ConstructDto getConstructInfoByRealEstateId(Long realEstateId) {

        Optional<ConstructDto> findConstruct = queryRepository.getConstructInfoByRealEstateId(realEstateId);
        if (findConstruct.isEmpty()) {
            throw new NotFoundException(realEstateId + "의 매물 데이터가 없습니다.");
        }

        return findConstruct.get();
    }

    @Override
    public List<FloorListDto> getFloorInfoByRealEstateId(Long realEstateId) {
        return queryRepository.getFloorInfoByRealEstateId(realEstateId);
    }

    @Override
    public FloorPriceInfo getConstructFloorById(Long floorId) {
        return queryRepository.getFloorInfoById(floorId);
    }
}
