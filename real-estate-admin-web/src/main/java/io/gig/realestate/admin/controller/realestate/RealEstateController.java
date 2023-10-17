package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.RealEstateService;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateUpdateForm;
import io.gig.realestate.domain.realestate.basic.dto.StatusUpdateForm;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
    private final AreaService areaService;

    @GetMapping
    public String index(RealEstateSearchDto searchDto, Model model) {

        List<AreaListDto> sidoList = areaService.getParentAreaList();
        model.addAttribute("sidoList", sidoList);

        if (StringUtils.hasText(searchDto.getSido())) {
            List<AreaListDto> gunguList = areaService.getAreaListBySido(searchDto.getSido());
            model.addAttribute("gunguList", gunguList);
        }

        if (StringUtils.hasText(searchDto.getGungu())) {
            List<AreaListDto> dongList = areaService.getAreaListByGungu(searchDto.getGungu());
            model.addAttribute("dongList", dongList);
        }

        List<CategoryDto> usageCds = categoryService.getChildrenCategoryDtosByName("용도변경-멸실가능");


        model.addAttribute("usageCds", usageCds);
        model.addAttribute("condition", searchDto);
        model.addAttribute("pages", realEstateService.getRealEstatePageListBySearch(searchDto));
        return "realestate/list";
    }

    @GetMapping("new")
    public String register(
            @RequestParam(name = "bCode") String legalCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "usageCdId") Long usageCdId,
            Model model,
            @CurrentUser LoginUser loginUser) {

        RealEstateDetailDto dto = RealEstateDetailDto.initDetailDto(legalCode, landType, bun, ji, address, usageCdId);
        List<AdministratorListDto> admins = administratorService.getAdminListMyMembers(loginUser);
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");

        model.addAttribute("dto", dto);
        model.addAttribute("admins", admins);
        model.addAttribute("usageCds", usageCds);

        return "realestate/editor";
    }

    @GetMapping("{realEstateId}/edit")
    public String editForm(@PathVariable(name = "realEstateId") Long realEstateId, Model model, @CurrentUser LoginUser loginUser) {

        RealEstateDetailDto dto = realEstateService.getDetail(realEstateId);
        dto.checkIsOwnUser(loginUser);
        List<AdministratorListDto> admins = administratorService.getAdminListMyMembers(loginUser);
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");

        model.addAttribute("dto", dto);
        model.addAttribute("admins", admins);
        model.addAttribute("usageCds", usageCds);

        return "realestate/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody RealEstateCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody RealEstateUpdateForm updateForm,
                                              @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.update(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PostMapping("basic")
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody RealEstateCreateForm createForm,
                                                 @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.basicInfoSave(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PutMapping("basic")
    @ResponseBody
    public ResponseEntity<ApiResponse> update2(@Valid @RequestBody RealEstateUpdateForm updateForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.basicInfoUpdate(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PutMapping("process")
    @ResponseBody
    public ResponseEntity<ApiResponse> processUpdate(@Valid @RequestBody RealEstateUpdateForm updateForm,
                                                     @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.updateProcessStatus(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PutMapping("status/r")
    @ResponseBody
    public ResponseEntity<ApiResponse> rStatusUpdate(@Valid @RequestBody StatusUpdateForm updateForm,
                                                     @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.updateRStatus(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PutMapping("status/ab")
    @ResponseBody
    public ResponseEntity<ApiResponse> abStatusUpdate(@Valid @RequestBody StatusUpdateForm updateForm,
                                                     @CurrentUser LoginUser loginUser) {
        Long realEstateId = realEstateService.updateABStatus(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }
}
