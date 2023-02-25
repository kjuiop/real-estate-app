package io.gig.realestate.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : JAKE
 * @date : 2023/02/25
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

}
