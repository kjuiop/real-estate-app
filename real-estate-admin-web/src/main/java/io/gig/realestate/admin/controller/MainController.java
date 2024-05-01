package io.gig.realestate.admin.controller;

import io.gig.realestate.domain.utils.InitUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : JAKE
 * @date : 2023/02/25
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    private final InitUtils initUtils;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/test/{error}")
    public ModelAndView test(
            @PathVariable(name = "error") int errorCode
    ) throws Exception {
        if (errorCode == 1) {
            throw new Exception("gg");
        }
        return new ModelAndView("index");
    }

    @GetMapping("init-data")
    public String initData() {
        initUtils.initData();
        return "redirect:/login";
    }

}
