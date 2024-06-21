package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.*;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.team.dto.TeamUpdateForm;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/25
 */
@Controller
@RequestMapping("settings/administrators")
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdministratorService administratorService;
    private final RoleService roleService;
    private final TeamService teamService;

    @GetMapping
    public String index(AdminSearchDto searchDto, Model model) {
        model.addAttribute("pages", administratorService.getAdminPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "settings/administrator/list";
    }

    @GetMapping("new")
    public String register(Model model) {

        List<RoleDto> roles = roleService.getAllRoles();
        AdministratorDetailDto dto = AdministratorDetailDto.emptyDto();
        List<TeamListDto> teams = teamService.getTeamList();

        model.addAttribute("roles", roles);
        model.addAttribute("dto", dto);
        model.addAttribute("teams", teams);

        return "settings/administrator/editor";
    }

    @GetMapping("{adminId}/edit")
    public String editForm(@PathVariable(name = "adminId") Long adminId, Model model) {
        List<RoleDto> roles = roleService.getAllRoles();
        AdministratorDetailDto dto = administratorService.getDetail(adminId);
        List<TeamListDto> teams = teamService.getTeamList();

        model.addAttribute("roles", roles);
        model.addAttribute("dto", dto);
        model.addAttribute("teams", teams);

        return "settings/administrator/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody AdministratorCreateForm createForm) {
        Long adminId = administratorService.create(createForm);
        return new ResponseEntity<>(ApiResponse.OK(adminId), HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody AdministratorUpdateForm updateForm) {
        Long adminId = administratorService.update(updateForm);
        return new ResponseEntity<>(ApiResponse.OK(adminId), HttpStatus.OK);
    }

    @PutMapping("status")
    @ResponseBody
    public ResponseEntity<ApiResponse> statusUpdate(@Valid @RequestBody List<AdminStatusUpdateForm> updateForm) {
        administratorService.statusUpdate(updateForm);
        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }

    @PostMapping("remove")
    @ResponseBody
    public ResponseEntity<ApiResponse> remove(@Valid @RequestBody List<AdminStatusUpdateForm> updateForm,
                                              @CurrentUser LoginUser loginUser) {
        administratorService.remove(loginUser, updateForm);
        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }

    @GetMapping("check-duplicate/username/{value}")
    @ResponseBody
    public ResponseEntity<ApiResponse> checkDuplicateData(
            @PathVariable(value = "value") String value) {

        boolean isDuplicate = administratorService.existsUsername(value);
        return new ResponseEntity<>(ApiResponse.OK(isDuplicate), HttpStatus.OK);
    }

    @PutMapping("team/{teamId}")
    @ResponseBody
    public ResponseEntity<ApiResponse> teamUpdate(@PathVariable(name = "teamId") Long teamId,
                                                  @Valid @RequestBody List<AdministratorTemUpdateForm> updateForm) {
        administratorService.teamUpdate(teamId, updateForm);
        return new ResponseEntity<>(ApiResponse.OK(teamId), HttpStatus.OK);
    }
}
