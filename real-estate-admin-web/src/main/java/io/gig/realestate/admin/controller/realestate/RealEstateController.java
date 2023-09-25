package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.RealEstateService;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.LandDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
    private final LandService landService;

    @GetMapping
    public String index(RealEstateSearchDto searchDto, Model model) {
        model.addAttribute("pages", realEstateService.getRealEstatePageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "realestate/list";
    }

    @GetMapping("new")
    public String register(
            @RequestParam(name = "pnu") String pnu,
            @RequestParam(name = "address") String address,
            Model model,
            @CurrentUser LoginUser loginUser) throws IOException {

        List<AdministratorListDto> admins = administratorService.getAdminListMyMembers(loginUser);
        RealEstateDetailDto dto = RealEstateDetailDto.initDetailDto(address, pnu);
        List<CategoryDto> processCds = categoryService.getChildrenCategoryDtosByName("진행구분");
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");
        List<LandDto> landList = landService.getLandListInfoByPnu(pnu);


        model.addAttribute("dto", dto);
        model.addAttribute("admins", admins);
        model.addAttribute("processCds", processCds);
        model.addAttribute("usageCds", usageCds);
        model.addAttribute("landList", landList);

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
