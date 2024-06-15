package io.gig.realestate.admin.controller.scheduler;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.SchedulerService;
import io.gig.realestate.domain.scheduler.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.dto.SchedulerDto;
import io.gig.realestate.domain.scheduler.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.dto.SchedulerListDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/15
 */
@Controller
@RequestMapping("scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> getSchedulers(@CurrentUser LoginUser loginUser) {
        List<SchedulerListDto> dto = schedulerService.getSchedulers(loginUser);
        return new ResponseEntity<>(ApiResponse.OK(dto), HttpStatus.OK);
    }

    @GetMapping("{schedulerId}")
    @ResponseBody
    public ResponseEntity<ApiResponse> getSchedulerById(@PathVariable(name = "schedulerId") Long schedulerId,
                                                        @CurrentUser LoginUser loginUser) {
        SchedulerDetailDto dto = schedulerService.getSchedulerById(schedulerId, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(dto), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody SchedulerForm createForm,
                                              @CurrentUser LoginUser loginUser) {
        Long schedulerId = schedulerService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(schedulerId), HttpStatus.OK);
    }
}
