package com.github.bali.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("personal")
public class PersonalController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "personal/index";
    }



    @RequestMapping("/avatar")
    public String avatar(Model model) {
        return "personal/avatar";
    }

}
