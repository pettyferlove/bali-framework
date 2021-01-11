package com.github.bali.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Petty
 */
@Controller
public class LoginController {
    @GetMapping(value = {"/login"})
    public String loginPage(Model model) {
        model.addAttribute("loginProcessUrl", "/authorize");
        return "login";
    }
}
