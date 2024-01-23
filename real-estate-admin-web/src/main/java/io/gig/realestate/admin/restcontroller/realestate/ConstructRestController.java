package io.gig.realestate.admin.restcontroller.realestate;

import io.gig.realestate.admin.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 2024/01/19
 */
@RestController
@RequestMapping("rest/real-estate/construct")
@RequiredArgsConstructor
public class ConstructRestController {

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> register() {



        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }
}
