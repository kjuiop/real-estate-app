package io.gig.realestate.admin.controller.realestate.construct;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.construct.ConstructService;
import io.gig.realestate.domain.realestate.construct.dto.ConstructCreateForm;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import io.gig.realestate.domain.realestate.land.dto.LandCreateForm;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2023/10/01
 */
@Controller
@RequestMapping("real-estate/construct")
@RequiredArgsConstructor
public class ConstructController {

    private final ConstructService constructService;

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

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody ConstructCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = constructService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }
}
