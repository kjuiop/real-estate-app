package io.gig.realestate.domain.realestate.print.repository;

import io.gig.realestate.domain.realestate.print.PrintInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/11/13
 */
@Repository
public interface PrintStoreRepository extends JpaRepository<PrintInfo, Long> {
}
