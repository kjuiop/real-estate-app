package io.gig.realestate.admin.controller.team;

import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.dto.TeamDetailDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/14
 */
@Controller
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamController {

    private final AdministratorService administratorService;
    private final TeamService teamService;

    @GetMapping
    public String register(AdminSearchDto searchDto, Model model, @CurrentUser LoginUser loginUser) {

        TeamDetailDto dto = teamService.getDetail(loginUser.getTeamId());
        Page<AdministratorListDto> teamMembers = administratorService.getAdminByTeamId(searchDto, loginUser.getUsername());

        model.addAttribute("dto", dto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pages", teamMembers);

        return "team/editor";
    }
}
