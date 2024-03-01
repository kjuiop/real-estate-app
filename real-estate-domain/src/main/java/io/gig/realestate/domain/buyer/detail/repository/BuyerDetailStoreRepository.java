package io.gig.realestate.domain.buyer.detail.repository;

import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Repository
public interface BuyerDetailStoreRepository extends JpaRepository<BuyerDetail, Long> {
}
