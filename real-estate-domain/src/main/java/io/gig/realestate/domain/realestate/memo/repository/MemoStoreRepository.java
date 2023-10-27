package io.gig.realestate.domain.realestate.memo.repository;

import io.gig.realestate.domain.realestate.memo.MemoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/10/09
 */
@Repository
public interface MemoStoreRepository extends JpaRepository<MemoInfo, Long> {
}
