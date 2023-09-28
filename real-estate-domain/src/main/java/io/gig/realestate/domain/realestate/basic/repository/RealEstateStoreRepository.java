package io.gig.realestate.domain.realestate.basic.repository;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Repository
public interface RealEstateStoreRepository extends JpaRepository<RealEstate, Long> {
}
