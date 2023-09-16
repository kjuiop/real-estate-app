package io.gig.realestate.admin.controller.auth;

import io.gig.realestate.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author : JAKE
 * @date : 2023/02/28
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final TeamService teamService;

    @GetMapping("login")
    public String login(HttpServletRequest request,
                        @RequestParam(name = "error", required = false) String error,
                        Model model) {

        if (error != null) {
            HttpSession session = request.getSession();
            String exceptionMessage = (String) session.getAttribute("exceptionMessage");
            model.addAttribute("exceptionMessage", exceptionMessage);
        }

        model.addAttribute("teams", teamService.getTeamList());

        return "login";
    }

}
