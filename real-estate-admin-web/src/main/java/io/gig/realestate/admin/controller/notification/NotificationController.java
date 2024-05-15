package io.gig.realestate.admin.controller.notification;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.notification.NotificationService;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2024/05/15
 */
@Controller
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("{notiId}/read")
    @ResponseBody
    public ResponseEntity<ApiResponse> read(@PathVariable(name="notiId") Long notiId,
                                            @Valid @RequestBody NotificationForm readForm,
                                            @CurrentUser LoginUser loginUser
                                            ) {
        Long notiCnt = notificationService.read(notiId, readForm, loginUser.getLoginUser());
        return new ResponseEntity<>(ApiResponse.OK(notiCnt), HttpStatus.OK);
    }

}
