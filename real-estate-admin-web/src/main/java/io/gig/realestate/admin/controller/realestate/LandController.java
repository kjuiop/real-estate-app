package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.*;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Controller
@RequestMapping("real-estate/land")
@RequiredArgsConstructor
public class LandController {

    private final LandService landService;

    @GetMapping("{realEstateId}")
    public ResponseEntity<ApiResponse> getLandData(
            @PathVariable(name = "realEstateId") Long realEstateId) {
        List<LandListDto> landList = landService.getLandListInfoByRealEstateId(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(landList), HttpStatus.OK);
    }

    @GetMapping("ajax/public-data")
    public ResponseEntity<ApiResponse> getPublicData(
            @RequestParam(name = "legalCode") String legalCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji
    ) throws IOException {
        List<LandDataApiDto> landList = landService.getLandListInfo(legalCode, landType, bun, ji);
        return new ResponseEntity<>(ApiResponse.OK(landList), HttpStatus.OK);
    }

    @GetMapping("usage/ajax/public-data")
    public ResponseEntity<ApiResponse> getLandUsagePublicData(
            @RequestParam(name = "legalCode") String legalCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji
    ) throws IOException {
        LandUsageDataApiDto data = landService.getLandUsagePublicData(legalCode, landType, bun, ji);
        return new ResponseEntity<>(ApiResponse.OK(data), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody LandCreateForm createForm,
                                                 @CurrentUser LoginUser loginUser) {
        Long realEstateId = landService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody LandCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = landService.update(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }
}
