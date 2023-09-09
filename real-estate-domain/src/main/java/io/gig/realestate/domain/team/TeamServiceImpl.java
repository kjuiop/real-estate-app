package io.gig.realestate.domain.team;

import io.gig.realestate.domain.team.dto.TeamCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamStore teamStore;

    @Override
    @Transactional
    public Long create(TeamCreateForm createForm) {
        Team newTeam = Team.create(createForm);
        return teamStore.store(newTeam).getId();
    }
}
