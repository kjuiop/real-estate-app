package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.dto.TeamCreateForm;
import io.gig.realestate.domain.team.dto.TeamDetailDto;
import io.gig.realestate.domain.team.dto.TeamSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/09
 */
@Controller
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamManagerController {

    private final TeamService teamService;
    private final AdministratorService administratorService;

    @GetMapping
    public String index(TeamSearchDto searchDto, Model model) {
        model.addAttribute("pages", teamService.getTeamPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "team/list";
    }

    @GetMapping("new")
    public String register(AdminSearchDto searchDto, Model model) {

        TeamDetailDto dto = TeamDetailDto.emptyDto();
        Page<AdministratorListDto> managerCandidates = administratorService.getCandidateManagers(searchDto);
        Page<AdministratorListDto> memberCandidates = administratorService.getCandidateMembers(searchDto);

        model.addAttribute("dto", dto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("managerCandidates", managerCandidates);
        model.addAttribute("memberCandidates", memberCandidates);

        return "team/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody TeamCreateForm createForm) {
        Long teamId = teamService.create(createForm);
        return new ResponseEntity<>(ApiResponse.OK(teamId), HttpStatus.OK);
    }

}
