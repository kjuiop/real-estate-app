package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2023/12/20
 */
@Controller
@RequestMapping("settings/coordinate-manager")
@RequiredArgsConstructor
public class CoordinateManagerController {

    @GetMapping
    public String index() {
        return "settings/coordinate/coordinate-manager";
    }

    @PostMapping("file/read")
    @ResponseBody
    public ResponseEntity<ApiResponse> readExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }
}
