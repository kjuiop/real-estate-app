package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.category.dto.CategoryUpdateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Controller
@RequestMapping("settings/area-manager")
@RequiredArgsConstructor
public class AreaCodeManagerController {


    @GetMapping
    public String index() {
        return "settings/area/area-manager";
    }

    @PostMapping("excel/read")
    @ResponseBody
    public ResponseEntity<ApiResponse> readExcel(@RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }
}
