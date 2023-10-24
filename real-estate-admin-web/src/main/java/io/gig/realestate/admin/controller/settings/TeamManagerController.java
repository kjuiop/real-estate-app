package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.dto.*;
import io.gig.realestate.domain.utils.CurrentUser;
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
@RequestMapping("/settings/team-manager")
@RequiredArgsConstructor
public class TeamManagerController {

    private final TeamService teamService;
    private final AdministratorService administratorService;

    @GetMapping
    public String index(TeamSearchDto searchDto, Model model) {
        model.addAttribute("pages", teamService.getTeamPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "/settings/team/list";
    }

    @GetMapping("new")
    public String register(Model model) {

        TeamDetailDto dto = TeamDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "/settings/team/editor";
    }

    @GetMapping("{teamId}/edit")
    public String editForm(@PathVariable(name = "teamId") Long teamId, AdminSearchDto searchDto, Model model, @CurrentUser LoginUser loginUser) {
        TeamDetailDto dto = teamService.getDetail(teamId);

        Page<AdministratorListDto> memberCandidates = administratorService.getCandidateMembers(searchDto, loginUser.getUsername());
        List<TeamListDto> teamList = teamService.getTeamList();

        model.addAttribute("dto", dto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("teamList", teamList);
        model.addAttribute("memberCandidates", memberCandidates);

        return "/settings/team/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody TeamCreateForm createForm, @CurrentUser LoginUser loginUser) {
        Long teamId = teamService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(teamId), HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody TeamUpdateForm updateForm, @CurrentUser LoginUser loginUser) {
        Long teamId = teamService.update(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(teamId), HttpStatus.OK);
    }

}
