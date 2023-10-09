package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.construct.ConstructService;
import io.gig.realestate.domain.realestate.construct.dto.*;
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
 * @date : 2023/10/01
 */
@Controller
@RequestMapping("real-estate/construct")
@RequiredArgsConstructor
public class ConstructController {

    private final ConstructService constructService;

    @GetMapping("{realEstateId}")
    public ResponseEntity<ApiResponse> getConstructData(
            @PathVariable(name = "realEstateId") Long realEstateId) {
        ConstructDto constructDto = constructService.getConstructInfoByRealEstateId(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(constructDto), HttpStatus.OK);
    }

    @GetMapping("floor/{realEstateId}")
    public ResponseEntity<ApiResponse> getFloorData(
            @PathVariable(name = "realEstateId") Long realEstateId) {
        List<FloorListDto> floorInfo = constructService.getFloorInfoByRealEstateId(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(floorInfo), HttpStatus.OK);
    }

    @GetMapping("ajax/public-data")
    public ResponseEntity<ApiResponse> getPublicData(
            @RequestParam(name = "legalCode") String legalCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji
    ) throws IOException {
        ConstructDataApiDto constructInfo = constructService.getConstructInfo(legalCode, landType, bun, ji);
        return new ResponseEntity<>(ApiResponse.OK(constructInfo), HttpStatus.OK);
    }

    @GetMapping("floor/ajax/public-data")
    public ResponseEntity<ApiResponse> getFloorPublicData(
            @RequestParam(name = "legalCode") String legalCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji
    ) throws IOException {
        List<ConstructFloorDataApiDto> floorInfo = constructService.getConstructFloorInfo(legalCode, landType, bun, ji);
        return new ResponseEntity<>(ApiResponse.OK(floorInfo), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody ConstructCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = constructService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }
}
