package io.gig.realestate.admin.controller.buyer;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.buyer.basic.BuyerService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerForm;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.detail.BuyerDetailService;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2024/02/17
 */
@Controller
@RequestMapping("buyer")
@RequiredArgsConstructor
public class BuyerController {

    private final CategoryService categoryService;
    private final BuyerService buyerService;
    private final TeamService teamService;

    @GetMapping
    public String index(BuyerSearchDto condition, Model model) {
        Page<BuyerListDto> pages = buyerService.getBuyerPageListBySearch(condition);
        model.addAttribute("totalCount", pages.getTotalElements());
        model.addAttribute("pages", pages);
        model.addAttribute("condition", condition);
        model.addAttribute("buyerGradeCds", categoryService.getChildrenCategoryDtosByCode("CD_BUYER_GRADE"));
        model.addAttribute("purposeCds", categoryService.getChildrenCategoryDtosByCode("CD_PURPOSE"));
        model.addAttribute("processCds", categoryService.getChildrenCategoryDtosByCode("CD_PROCESS"));
        model.addAttribute("teams", teamService.getTeamList());
        return "buyer/list";
    }

    @GetMapping("new")
    public String register(Model model) {
        model.addAttribute("dto", BuyerDetailDto.emptyDto());
        model.addAttribute("buyerGradeCds", categoryService.getChildrenCategoryDtosByCode("CD_BUYER_GRADE"));
        model.addAttribute("characterCds", categoryService.getChildrenCategoryDtosByCode("CD_INVESTMENT_CHARACTER"));
        model.addAttribute("purposeCds", categoryService.getChildrenCategoryDtosByCode("CD_PURPOSE"));
        model.addAttribute("loanCharacterCds", categoryService.getChildrenCategoryDtosByCode("CD_LOAN_CHARACTER"));
        model.addAttribute("preferBuildingCds", categoryService.getChildrenCategoryDtosByCode("CD_PREFER_BUILDING"));
        model.addAttribute("investmentTimingCds", categoryService.getChildrenCategoryDtosByCode("CD_INVESTMENT_TIMING"));
        return "buyer/editor";
    }

    @GetMapping("{buyerId}/edit")
    public String editForm(@PathVariable(name = "buyerId") Long buyerId,
                           Model model) {
        model.addAttribute("dto", buyerService.getBuyerDetail(buyerId));
        model.addAttribute("buyerGradeCds", categoryService.getChildrenCategoryDtosByCode("CD_BUYER_GRADE"));
        model.addAttribute("characterCds", categoryService.getChildrenCategoryDtosByCode("CD_INVESTMENT_CHARACTER"));
        model.addAttribute("purposeCds", categoryService.getChildrenCategoryDtosByCode("CD_PURPOSE"));
        model.addAttribute("loanCharacterCds", categoryService.getChildrenCategoryDtosByCode("CD_LOAN_CHARACTER"));
        model.addAttribute("preferBuildingCds", categoryService.getChildrenCategoryDtosByCode("CD_PREFER_BUILDING"));
        model.addAttribute("investmentTimingCds", categoryService.getChildrenCategoryDtosByCode("CD_INVESTMENT_TIMING"));
        return "buyer/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody BuyerForm createForm,
                                              @CurrentUser LoginUser loginUser) {
        Long buyerId = buyerService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(buyerId), HttpStatus.OK);
    }

    @PutMapping()
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody BuyerForm updateForm,
                                              @CurrentUser LoginUser loginUser) {
        Long id = buyerService.update(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(id), HttpStatus.OK);
    }
}
