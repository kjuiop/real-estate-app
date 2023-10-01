package io.gig.realestate.admin.controller.sample;

import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2023/09/02
 */
@Controller
@RequestMapping("sample")
@RequiredArgsConstructor
public class SampleController {

    @GetMapping("realestate/sample")
    public String sample(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "sample/realestate/sample";
    }

    @GetMapping("realestate/sample2")
    public String sample2(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "sample/realestate/sample2";
    }

    @GetMapping("modal")
    public String modal() {
        return "sample/modal/modal";
    }

    @GetMapping("partner/editor")
    public String partnerEditor(Model model) {
        return "sample/partner/editor";
    }

    @GetMapping("partner/info")
    public String partnerInfo(Model model) {
        return "sample/partner/info";
    }

    @GetMapping("partner/login")
    public String partnerLogin(Model model) {
        return "sample/partner/login";
    }
}
