package io.gig.realestate.admin.controller.settings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Controller
@RequestMapping("settings/category-manager")
@RequiredArgsConstructor
public class CategoryManagerController {

    @GetMapping
    public String index() {
        return "settings/category/category-manager";
    }

}
