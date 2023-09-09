package io.gig.realestate.domain.team.repository;

import io.gig.realestate.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Repository
public interface TeamStoreRepository extends JpaRepository<Team, Long> {
}
