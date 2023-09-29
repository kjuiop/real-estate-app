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
import io.gig.realestate.domain.realestate.construct.ConstructService;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
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
    private final ConstructService constructService;

    @GetMapping
    public String index(RealEstateSearchDto searchDto, Model model) {
        model.addAttribute("pages", realEstateService.getRealEstatePageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "realestate/list";
    }

    @GetMapping("new")
    public String register(
            @RequestParam(name = "bCode") String bCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji,
            @RequestParam(name = "address") String address,
            Model model,
            @CurrentUser LoginUser loginUser) throws IOException {

        RealEstateDetailDto dto = RealEstateDetailDto.initDetailDto(address);
        List<LandDataApiDto> landList = landService.getLandListInfo(bCode, landType, bun, ji);
        ConstructDataApiDto constructInfo = constructService.getConstructInfo(bCode, landType, bun, ji);

        List<AdministratorListDto> admins = administratorService.getAdminListMyMembers(loginUser);
        List<CategoryDto> processCds = categoryService.getChildrenCategoryDtosByName("진행구분");
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");

        model.addAttribute("dto", dto);
        model.addAttribute("admins", admins);
        model.addAttribute("processCds", processCds);
        model.addAttribute("usageCds", usageCds);
        model.addAttribute("landInfo", landList.get(0));
        model.addAttribute("constructInfo", constructInfo);
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
