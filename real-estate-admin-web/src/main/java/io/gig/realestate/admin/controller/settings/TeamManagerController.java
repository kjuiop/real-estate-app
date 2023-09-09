package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.dto.TeamCreateForm;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Controller
@RequestMapping("settings/team")
@RequiredArgsConstructor
public class TeamManagerController {

    private final TeamService teamService;

    @GetMapping
    public String index(TeamSearchDto searchDto, Model model) {
        model.addAttribute("pages", teamService.getTeamPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "settings/team/list";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody TeamCreateForm createForm) {
        Long teamId = teamService.create(createForm);
        return new ResponseEntity<>(ApiResponse.OK(teamId), HttpStatus.OK);
    }

}
