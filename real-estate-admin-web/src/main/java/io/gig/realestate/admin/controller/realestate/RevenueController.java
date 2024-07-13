package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.revenue.dto.RevenueDetailDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2024/07/10
 */
@Controller
@RequestMapping("real-estate/revenue")
@RequiredArgsConstructor
public class RevenueController {

    @GetMapping("{realEstateId}/edit")
    public String editForm(@PathVariable(name = "realEstateId") Long realEstateId,
                           Model model,
                           @CurrentUser LoginUser loginUser) {

        model.addAttribute("dto", RevenueDetailDto.emptyDto(realEstateId));
        model.addAttribute("loginUser", loginUser);

        return "realestate/revenue/editor";
    }
}
