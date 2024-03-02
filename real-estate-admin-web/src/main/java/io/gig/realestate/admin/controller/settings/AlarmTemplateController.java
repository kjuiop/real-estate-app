package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.message.template.AlarmTemplateService;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateForm;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateSearchDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Controller
@RequestMapping("settings/alarm-template")
@RequiredArgsConstructor
public class AlarmTemplateController {

    private final AlarmTemplateService alarmTemplateService;

    @GetMapping
    public String index(AlarmTemplateSearchDto condition, Model model) {
        model.addAttribute("totalCount", 0);
        model.addAttribute("pages", null);
        model.addAttribute("condition", condition);
        return "settings/alarm-template/list";
    }

    @GetMapping("new")
    public String register(Model model) {
        model.addAttribute("dto", AlarmTemplateDetailDto.emptyDto());
        return "settings/alarm-template/editor";
    }

    @GetMapping("{alarmTemplateId}/edit")
    public String editForm(@PathVariable(name = "alarmTemplateId") Long alarmTemplateId,
                           Model model) {
        model.addAttribute("dto", alarmTemplateService.getAlarmTemplateDetail(alarmTemplateId));
        return "settings/alarm-template/editor";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody AlarmTemplateForm createForm,
                                              @CurrentUser LoginUser loginUser) {
        Long alarmTemplateId = alarmTemplateService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(alarmTemplateId), HttpStatus.OK);
    }

}
