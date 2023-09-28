package io.gig.realestate.admin.controller.map;

import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
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
@RequestMapping("map")
@RequiredArgsConstructor
public class MapController {

    @GetMapping("sample")
    public String sample(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        model.addAttribute("dto", dto);

        return "map/map";
    }
}
