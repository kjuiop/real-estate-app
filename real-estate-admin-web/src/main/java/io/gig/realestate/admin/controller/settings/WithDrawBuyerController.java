package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.BuyerService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2024/06/22
 */
@Controller
@RequestMapping("settings/withdraw-buyer")
@RequiredArgsConstructor
public class WithDrawBuyerController {

    private final BuyerService buyerService;
    private final CategoryService categoryService;
    private final TeamService teamService;

    @GetMapping
    public String index(BuyerSearchDto condition, @CurrentUser LoginUser loginUser, Model model) {
        condition.setWithDraw(true);
        Page<BuyerListDto> pages = buyerService.getBuyerPageListBySearch(condition, loginUser);
        model.addAttribute("totalCount", pages.getTotalElements());
        model.addAttribute("pages", pages);
        model.addAttribute("condition", condition);
        model.addAttribute("buyerGradeCds", categoryService.getChildrenCategoryDtosByCode("CD_BUYER_GRADE"));
        model.addAttribute("purposeCds", categoryService.getChildrenCategoryDtosByCode("CD_PURPOSE"));
        model.addAttribute("processCds", categoryService.getChildrenCategoryDtosByCode("CD_PROCESS"));
        model.addAttribute("teams", teamService.getTeamList());
        return "settings/withdraw/buyer/list";
    }


}
