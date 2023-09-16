package io.gig.realestate.admin.controller.auth;

import io.gig.realestate.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : JAKE
 * @date : 2023/02/28
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final TeamService teamService;

    @GetMapping("login")
    public String login(Model model) {

        model.addAttribute("teams", teamService.getTeamList());

        return "login";
    }

}
