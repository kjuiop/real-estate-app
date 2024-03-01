package io.gig.realestate.admin.controller.buyer;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.buyer.basic.BuyerService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerCreateForm;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.detail.BuyerDetailService;
import io.gig.realestate.domain.buyer.detail.dto.BuyerDetailUpdateForm;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
import io.gig.realestate.domain.category.CategoryService;
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
    private final AreaService areaService;
    private final BuyerService buyerService;
    private final BuyerDetailService buyerDetailService;

    @GetMapping
    public String index(BuyerSearchDto condition, Model model) {
        Page<BuyerListDto> pages = buyerService.getBuyerPageListBySearch(condition);
        model.addAttribute("totalCount", pages.getTotalElements());
        model.addAttribute("pages", pages);
        model.addAttribute("condition", condition);
        model.addAttribute("processCds", categoryService.getChildrenCategoryDtosByCode("CD_PROCESS"));
        return "buyer/list";
    }

    @GetMapping("new")
    public String register(Model model) {
        BuyerDetailDto dto = BuyerDetailDto.emptyDto();
        model.addAttribute("sidoList", areaService.getParentAreaList());
        model.addAttribute("processCds", categoryService.getChildrenCategoryDtosByCode("CD_PROCESS"));
        model.addAttribute("usageCds", categoryService.getChildrenCategoryDtosByCode("CD_USAGE_01"));
        model.addAttribute("characterCds", categoryService.getChildrenCategoryDtosByCode("CD_INVESTMENT_CHARACTER"));
        model.addAttribute("dto", dto);
        model.addAttribute("processDto", dto.getProcessDetailDto());
        return "buyer/editor";
    }

    @GetMapping("{buyerId}/edit")
    public String editForm(@PathVariable(name = "buyerId") Long buyerId,
                           Model model) {

        BuyerDetailDto dto = buyerService.getBuyerDetail(buyerId);

        model.addAttribute("sidoList", areaService.getParentAreaList());
        model.addAttribute("processCds", categoryService.getChildrenCategoryDtosByCode("CD_PROCESS"));
        model.addAttribute("usageCds", categoryService.getChildrenCategoryDtosByCode("CD_USAGE_01"));
        model.addAttribute("characterCds", categoryService.getChildrenCategoryDtosByCode("CD_INVESTMENT_CHARACTER"));
        model.addAttribute("dto", dto);
        model.addAttribute("processDto", dto.getProcessDetailDto());
        return "buyer/editor";
    }

    @GetMapping("{buyerId}/{processCd}")
    @ResponseBody
    public ResponseEntity<ApiResponse> getProcessDetail(@PathVariable(name = "buyerId") Long buyerId,
                                                      @PathVariable(name = "processCd") Long processCd) {
        ProcessDetailDto result = buyerDetailService.getBuyerDetailByProcessCd(buyerId, processCd);
        return new ResponseEntity<>(ApiResponse.OK(result), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody BuyerCreateForm createForm,
                                              @CurrentUser LoginUser loginUser) {
        Long buyerId = buyerService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(buyerId), HttpStatus.OK);
    }

    @PutMapping()
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody BuyerDetailUpdateForm updateForm,
                                              @CurrentUser LoginUser loginUser) {
        Long id = buyerService.update(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(id), HttpStatus.OK);
    }
}
