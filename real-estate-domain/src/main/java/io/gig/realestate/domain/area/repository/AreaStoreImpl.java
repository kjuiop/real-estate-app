package io.gig.realestate.domain.area.repository;

import io.gig.realestate.domain.area.Area;
import io.gig.realestate.domain.area.AreaStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Component
@Transactional
@RequiredArgsConstructor
public class AreaStoreImpl implements AreaStore {

    private final AreaStoreRepository areaStoreRepository;

    @Override
    public Area store(Area area) {
        return areaStoreRepository.save(area);
    }

    @Override
    public void storeAll(List<Area> areaList) {
        areaStoreRepository.saveAll(areaList);
    }
}
