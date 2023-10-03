package io.gig.realestate.domain.area.repository;

import io.gig.realestate.domain.area.AreaReader;
import io.gig.realestate.domain.area.dto.AreaListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AreaQueryImpl implements AreaReader {

    private final AreaQueryRepository queryRepository;

    @Override
    public List<AreaListDto> getParentAreaList() {
        return queryRepository.getParentAreaList();
    }

    @Override
    public List<AreaListDto> getAreaListByParentId(Long areaId) {
        return queryRepository.getAreaListByParentId(areaId);
    }
}
