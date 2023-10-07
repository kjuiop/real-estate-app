package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.category.dto.CategoryUpdateForm;
import io.gig.realestate.domain.realestate.memo.dto.MemoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Controller
@RequestMapping("settings/area-manager")
@RequiredArgsConstructor
public class AreaCodeManagerController {

    private final AreaService areaService;

    @GetMapping
    public String index() {
        return "settings/area/area-manager";
    }

    @PostMapping("excel/read")
    @ResponseBody
    public ResponseEntity<ApiResponse> readExcel(@RequestParam("file") MultipartFile file) throws IOException {
        areaService.createByExcelData(file);
        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }

    @GetMapping("{areaId}")
    public ResponseEntity<ApiResponse> getAreaListByParentId(
            @PathVariable(name = "areaId") Long areaId) {
        List<AreaListDto> areaList = areaService.getAreaListByParentId(areaId);
        return new ResponseEntity<>(ApiResponse.OK(areaList), HttpStatus.OK);
    }
}
