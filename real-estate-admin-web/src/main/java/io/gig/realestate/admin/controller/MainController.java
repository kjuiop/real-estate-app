package io.gig.realestate.admin.controller;

import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorDto;
import io.gig.realestate.domain.buyer.basic.BuyerService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.scheduler.basic.SchedulerService;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerSearchDto;
import io.gig.realestate.domain.utils.CurrentUser;
import io.gig.realestate.domain.utils.InitUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : JAKE
 * @date : 2023/02/25
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    private final InitUtils initUtils;
    private final AdministratorService administratorService;
    private final SchedulerService schedulerService;
    private final CategoryService categoryService;
    private final BuyerService buyerService;

    @GetMapping("/")
    public ModelAndView index(
            SchedulerSearchDto condition,
            HttpServletRequest request, @CurrentUser LoginUser loginUser) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("condition", condition);
        mav.addObject("loginUser", new AdministratorDto(loginUser.getLoginUser()));
        mav.addObject("admins", administratorService.getAdminListMyMembers(loginUser));
        mav.addObject("schedulers", schedulerService.getSchedulers(condition, loginUser));
        mav.addObject("buyerGradeCds", categoryService.getChildrenCategoryDtosByCode("CD_BUYER_GRADE"));
        mav.addObject("priorityOrderCds", categoryService.getChildrenCategoryDtosByCode("CD_PRIORITY_ORDER"));
        mav.addObject("buyerList", buyerService.getBuyerListByLoginUser(loginUser));
        if (request.getSession() != null) {
            mav.addObject("errorMessage", request.getSession().getAttribute("errorMessage"));
            request.getSession().removeAttribute("errorMessage");
        }
        return mav;
    }

    @GetMapping("/test/{error}")
    public ModelAndView test(
            @PathVariable(name = "error") int errorCode
    ) throws Exception {
        if (errorCode == 1) {
            throw new Exception("gg");
        }
        return new ModelAndView("index");
    }

    @GetMapping("init-data")
    public String initData() {
        initUtils.initData();
        return "redirect:/login";
    }

}
