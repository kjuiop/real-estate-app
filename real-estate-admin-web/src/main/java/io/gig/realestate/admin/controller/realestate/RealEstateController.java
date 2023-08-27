package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.domain.realestate.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.dto.RealEstateDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Controller
@RequestMapping("real-estate")
@RequiredArgsConstructor
public class RealEstateController {

    @GetMapping
    public String index(RealEstateSearchDto searchDto, Model model) {
        model.addAttribute("pages", null);
        model.addAttribute("condition", searchDto);
        return "realestate/list";
    }

    @GetMapping("new")
    public String register(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "realestate/editor";
    }

    @GetMapping("sample")
    public String sample(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "realestate/sample";
    }

    @GetMapping("sample2")
    public String sample2(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "realestate/sample2";
    }
}
