package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.domain.realestate.RealEstateSearchDto;
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
        return "real-estate/list";
    }
}
