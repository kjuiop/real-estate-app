package io.gig.realestate.admin.controller.realestate.land;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
