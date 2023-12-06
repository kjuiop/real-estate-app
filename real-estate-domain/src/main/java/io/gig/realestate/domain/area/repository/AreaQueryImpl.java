package io.gig.realestate.domain.area.repository;

import io.gig.realestate.domain.area.Area;
import io.gig.realestate.domain.area.AreaReader;
import io.gig.realestate.domain.area.dto.AreaListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<AreaListDto> getAreaListBySido(String sido) {
        return queryRepository.getAreaListBySido(sido);
    }

    @Override
    public List<AreaListDto> getAreaListByGungu(String gungu) {
        return queryRepository.getAreaListByGungu(gungu);
    }

    @Override
    public Optional<Area> getAreaLikeNameAndArea(String name, String sido, String gungu, String dong) {
        return queryRepository.getAreaLikeNameAndArea(name, sido, gungu, dong);
    }
}
