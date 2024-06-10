package io.gig.realestate.admin.controller.administrator;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.admin.dto.AdministratorSignUpForm;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.team.dto.TeamListDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/16
 */
@Controller
@RequestMapping("administrators")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @RequestMapping("check-duplicate/username/{value}")
    @ResponseBody
    public ResponseEntity<ApiResponse> checkDuplicateData(
            @PathVariable(value = "value") String value) {
        boolean isDuplicate = administratorService.existsUsername(value);
        return new ResponseEntity<>(ApiResponse.OK(isDuplicate), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody AdministratorSignUpForm signUpForm) {
        Long adminId = administratorService.signUp(signUpForm);
        return new ResponseEntity<>(ApiResponse.OK(adminId), HttpStatus.OK);
    }

    @GetMapping("team/{teamId}")
    @ResponseBody
    public ResponseEntity<ApiResponse> getAdminListByTeamId(
            @PathVariable(value = "teamId") Long teamId) {

        List<AdministratorListDto> data = administratorService.getAdminListByTeamId(teamId);
        return new ResponseEntity<>(ApiResponse.OK(data), HttpStatus.OK);
    }

}

