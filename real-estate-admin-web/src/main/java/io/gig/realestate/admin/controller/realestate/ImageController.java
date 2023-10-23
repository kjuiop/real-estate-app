package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.realestate.image.ImageService;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@Controller
@RequestMapping("real-estate/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("{realEstateId}")
    public ResponseEntity<ApiResponse> getImageData(
            @PathVariable(name = "realEstateId") Long realEstateId) {
        List<ImageDto> imageList = imageService.getSubImageInfoByRealEstateId(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(imageList), HttpStatus.OK);
    }

}
