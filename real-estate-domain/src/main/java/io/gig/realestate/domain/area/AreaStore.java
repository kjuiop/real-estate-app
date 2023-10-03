package io.gig.realestate.domain.area;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
public interface AreaStore {
    Area store(Area area);

    void storeAll(List<Area> areaList);
}
