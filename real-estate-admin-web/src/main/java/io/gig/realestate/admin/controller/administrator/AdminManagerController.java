package io.gig.realestate.admin.controller.administrator;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorCreateForm;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
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
@RequestMapping("administrators")
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdministratorService administratorService;
    private final RoleService roleService;

    @GetMapping
    public String index(AdminSearchDto searchDto, Model model) {
        model.addAttribute("pages", administratorService.getAdminPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "administrator/list";
    }

    @GetMapping("new")
    public String register(Model model) {
        List<RoleDto> roles = roleService.getAllRoles();
        AdministratorDetailDto dto = AdministratorDetailDto.emptyDto();

        model.addAttribute("roles", roles);
        model.addAttribute("dto", dto);

        return "administrator/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody AdministratorCreateForm createForm) {
        Long adminId = administratorService.create(createForm);
        return new ResponseEntity<>(ApiResponse.OK(adminId), HttpStatus.OK);
    }
}
