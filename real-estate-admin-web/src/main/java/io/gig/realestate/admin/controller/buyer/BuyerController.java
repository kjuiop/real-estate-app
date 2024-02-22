package io.gig.realestate.admin.controller.buyer;

import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.buyer.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/02/17
 */
@Controller
@RequestMapping("buyer")
@RequiredArgsConstructor
public class BuyerController {

    private final AreaService areaService;

    @GetMapping
    public String index(BuyerSearchDto condition, Model model) {
        model.addAttribute("condition", condition);
        return "buyer/list";
    }

    @GetMapping("new")
    public String register(Model model) {
        model.addAttribute("sidoList", areaService.getParentAreaList());
        model.addAttribute("dto", BuyerDetailDto.emptyDto());
        return "buyer/editor";
    }
}
