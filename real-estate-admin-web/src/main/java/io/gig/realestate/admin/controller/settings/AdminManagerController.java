package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.dto.*;
import io.gig.realestate.domain.role.RoleService;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public String index(AdminSearchDto searchDto, Model model) {
        model.addAttribute("pages", administratorService.getAdminPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "settings/administrator/list";
    }

    @GetMapping("new")
    public String register(Model model) {

        List<RoleDto> roles = roleService.getAllRoles();
        List<AdministratorListDto> notTeamAdmins = administratorService.getNotTeamAdmins();
        AdministratorDetailDto dto = AdministratorDetailDto.emptyDto();

        model.addAttribute("roles", roles);
        model.addAttribute("dto", dto);

        return "settings/administrator/editor";
    }

    @GetMapping("{adminId}/edit")
    public String editForm(@PathVariable(name = "adminId") Long adminId, Model model) {
        List<RoleDto> roles = roleService.getAllRoles();
        AdministratorDetailDto dto = administratorService.getDetail(adminId);

        model.addAttribute("roles", roles);
        model.addAttribute("dto", dto);

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

    @GetMapping("check-duplicate/username/{value}")
    @ResponseBody
    public ResponseEntity<ApiResponse> checkDuplicateData(
            @PathVariable(value = "value") String value) {

        boolean isDuplicate = administratorService.existsUsername(value);
        return new ResponseEntity<>(ApiResponse.OK(isDuplicate), HttpStatus.OK);
    }
}
