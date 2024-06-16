package io.gig.realestate.admin.controller.scheduler;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.scheduler.basic.SchedulerService;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerDetailDto;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerForm;
import io.gig.realestate.domain.scheduler.basic.dto.SchedulerListDto;
import io.gig.realestate.domain.team.TeamService;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody SchedulerForm updateForm,
                                              @CurrentUser LoginUser loginUser) {
        Long schedulerId = schedulerService.update(updateForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(schedulerId), HttpStatus.OK);
    }
}
