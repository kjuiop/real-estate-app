package io.gig.realestate.admin.controller.administrator;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : JAKE
 * @date : 2023/09/16
 */
@Controller
@RequestMapping("administrators")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @RequestMapping("check-duplicate/username/{value}")
    @ResponseBody
    public ResponseEntity<ApiResponse> checkDuplicateData(
            @PathVariable(value = "value") String value) {

        boolean isDuplicate = administratorService.existsUsername(value);
        return new ResponseEntity<>(ApiResponse.OK(isDuplicate), HttpStatus.OK);
    }
}

