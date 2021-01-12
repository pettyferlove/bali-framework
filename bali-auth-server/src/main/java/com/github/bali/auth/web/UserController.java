package com.github.bali.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Petty
 */
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "user/index";
    }

}
