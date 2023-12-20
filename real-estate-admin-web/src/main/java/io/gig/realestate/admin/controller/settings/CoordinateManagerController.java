package io.gig.realestate.admin.controller.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2023/12/20
 */
@Controller
@RequestMapping("settings/coordinate-manager")
@RequiredArgsConstructor
public class CoordinateManagerController {

    @GetMapping
    public String index() {
        return "settings/coordinate/coordinate-manager";
    }
}
