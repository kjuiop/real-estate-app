package io.gig.realestate.admin.controller.realestate.price;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.land.dto.LandCreateForm;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import io.gig.realestate.domain.realestate.price.PriceService;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceListDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Controller
@RequestMapping("real-estate/price")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("{realEstateId}")
    public ResponseEntity<ApiResponse> getPriceData(
            @PathVariable(name = "realEstateId") Long realEstateId) {
        List<PriceListDto> landList = priceService.getPriceListInfoByRealEstateId(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(landList), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody PriceCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = priceService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

}
