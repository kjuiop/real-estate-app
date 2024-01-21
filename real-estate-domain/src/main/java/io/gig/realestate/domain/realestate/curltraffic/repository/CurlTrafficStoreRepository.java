package io.gig.realestate.domain.realestate.curltraffic.repository;

import io.gig.realestate.domain.realestate.curltraffic.CurlTrafficLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/01/21
 */
@Repository
public interface CurlTrafficStoreRepository extends JpaRepository<CurlTrafficLight, Long> {
}
