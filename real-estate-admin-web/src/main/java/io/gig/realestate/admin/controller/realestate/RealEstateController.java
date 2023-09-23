package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.realestate.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.RealEstateService;
import io.gig.realestate.domain.realestate.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.dto.RealEstateDetailDto;
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
 * @date : 2023/04/14
 */
@Controller
@RequestMapping("real-estate")
@RequiredArgsConstructor
public class RealEstateController {

    private final CategoryService categoryService;
    private final AdministratorService administratorService;
    private final RealEstateService realEstateService;

    @GetMapping
    public String index(RealEstateSearchDto searchDto, Model model) {
        model.addAttribute("pages", realEstateService.getRealEstatePageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "realestate/list";
    }

    @GetMapping("new")
    public String register(
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "bCode", required = false) String bCode,
            @RequestParam(name = "hCode", required = false) String hCode,
            Model model,
            @CurrentUser LoginUser loginUser) {

        List<AdministratorListDto> admins = administratorService.getAdminListMyMembers(loginUser);
        RealEstateDetailDto dto = RealEstateDetailDto.initDetailDto(address, bCode, hCode);
        List<CategoryDto> processCds = categoryService.getChildrenCategoryDtosByName("진행구분");
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");

        model.addAttribute("dto", dto);
        model.addAttribute("admins", admins);
        model.addAttribute("processCds", processCds);
        model.addAttribute("usageCds", usageCds);

        return "realestate/editor";
    }

    @GetMapping("{realEstateId}/edit")
    public String editForm(@PathVariable(name = "realEstateId") Long realEstateId, Model model, @CurrentUser LoginUser loginUser) {

        List<AdministratorListDto> admins = administratorService.getAdminListMyMembers(loginUser);
        RealEstateDetailDto dto = realEstateService.getDetail(realEstateId);
        List<CategoryDto> processCds = categoryService.getChildrenCategoryDtosByName("진행구분");
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");

        model.addAttribute("dto", dto);
        model.addAttribute("admins", admins);
        model.addAttribute("processCds", processCds);
        model.addAttribute("usageCds", usageCds);

        return "realestate/editor";
    }

    @PostMapping("basic")
    @ResponseBody
    public ResponseEntity<ApiResponse> basicSave(@Valid @RequestBody RealEstateCreateForm createForm,
                                                 @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.basicInfoSave(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }
}
