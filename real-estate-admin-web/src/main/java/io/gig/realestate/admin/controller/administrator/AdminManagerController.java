package io.gig.realestate.admin.controller.administrator;

import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2023/03/25
 */
@Controller
@RequestMapping("administrators")
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdministratorService administratorService;

    @GetMapping
    public String index(AdminSearchDto searchDto, Model model) {
        model.addAttribute("pages", administratorService.getAdminPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "administrator/list";
    }


}
