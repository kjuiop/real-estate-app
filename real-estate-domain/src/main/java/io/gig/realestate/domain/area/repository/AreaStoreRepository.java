package io.gig.realestate.domain.area.repository;

import io.gig.realestate.domain.area.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Repository
public interface AreaStoreRepository extends JpaRepository<Area, Long> {
}
