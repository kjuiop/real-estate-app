package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateDetailDto;
import io.gig.realestate.domain.message.template.dto.AlarmTemplateSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Controller
@RequestMapping("settings/alarm-template")
@RequiredArgsConstructor
public class AlarmTemplateController {

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

}
