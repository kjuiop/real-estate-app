package io.gig.realestate.admin.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : JAKE
 * @date : 2023/02/28
 */
@Controller
public class AuthController {

    @GetMapping("login")
    public String login() {
        return "login";
    }

}
