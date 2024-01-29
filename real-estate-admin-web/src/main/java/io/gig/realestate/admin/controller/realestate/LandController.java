package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.*;
import io.gig.realestate.domain.realestate.landprice.LandPriceService;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceDataApiDto;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceListDto;
import io.gig.realestate.domain.realestate.landusage.LandUsageService;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
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
    private final LandPriceService landPriceService;
    private final LandUsageService landUsageService;

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

    @GetMapping("usage/{realEstateId}")
    public ResponseEntity<ApiResponse> getLandUsage(
            @PathVariable(name = "realEstateId") Long realEstateId
    ) throws IOException {
        LandUsageDto data = landUsageService.getLandUsageInfo(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(data), HttpStatus.OK);
    }

    @GetMapping("price/{realEstateId}")
    public ResponseEntity<ApiResponse> getLandPrice(
            @PathVariable(name = "realEstateId") Long realEstateId
    ) throws IOException {
        List<LandPriceListDto> priceList = landPriceService.getLandPriceListInfo(realEstateId);
        Collections.reverse(priceList);
        return new ResponseEntity<>(ApiResponse.OK(priceList), HttpStatus.OK);
    }

    @GetMapping("price/ajax/public-data")
    public ResponseEntity<ApiResponse> getLandPricePublicData(
            @RequestParam(name = "legalCode") String legalCode,
            @RequestParam(name = "landType") String landType,
            @RequestParam(name = "bun") String bun,
            @RequestParam(name = "ji") String ji
    ) throws IOException {
        List<LandPriceDataApiDto> priceInfo = landPriceService.getLandPricePublicData(legalCode, landType, bun, ji);
        return new ResponseEntity<>(ApiResponse.OK(priceInfo), HttpStatus.OK);
    }
}
