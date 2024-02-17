package io.gig.realestate.admin.controller.buyer;

import io.gig.realestate.domain.buyer.BuyerSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2024/02/17
 */
@Controller
@RequestMapping("buyer")
@RequiredArgsConstructor
public class BuyerController {

    @GetMapping
    public String index(BuyerSearchDto condition, Model model) {
        model.addAttribute("condition", condition);
        return "buyer/list";
    }
}
