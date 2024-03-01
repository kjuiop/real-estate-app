package io.gig.realestate.domain.buyer.basic.repository;

import io.gig.realestate.domain.buyer.basic.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Repository
public interface BuyerStoreRepository extends JpaRepository<Buyer, Long> {
}
