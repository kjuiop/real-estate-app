package io.gig.realestate.admin.controller.realestate.memo;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.memo.MemoService;
import io.gig.realestate.domain.realestate.memo.dto.MemoCreateForm;
import io.gig.realestate.domain.realestate.memo.dto.MemoDetailDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Controller
@RequestMapping("real-estate/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody MemoCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = memoService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

}
