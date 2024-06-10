package io.gig.realestate.admin.controller.mypage;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorUpdateForm;
import io.gig.realestate.domain.admin.dto.MyPageUpdateForm;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2024/06/10
 */
@Controller
@RequestMapping("mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final AdministratorService administratorService;

    @RequestMapping
    public String myPage(Model model, @CurrentUser LoginUser loginUser) {
        AdministratorDetailDto dto = administratorService.getDetail(loginUser.getId());
        model.addAttribute("dto", dto);
        return "administrator/editor";
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody MyPageUpdateForm updateForm) {
        Long adminId = administratorService.updateMyPage(updateForm);
        return new ResponseEntity<>(ApiResponse.OK(adminId), HttpStatus.OK);
    }
}
