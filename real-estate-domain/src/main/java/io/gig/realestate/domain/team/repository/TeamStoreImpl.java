package io.gig.realestate.domain.team.repository;

import io.gig.realestate.domain.team.Team;
import io.gig.realestate.domain.team.TeamStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Component
@Transactional
@RequiredArgsConstructor
public class TeamStoreImpl implements TeamStore {

    private final TeamStoreRepository teamStoreRepository;

    @Override
    public Team store(Team team) {
        return teamStoreRepository.save(team);
    }
}
