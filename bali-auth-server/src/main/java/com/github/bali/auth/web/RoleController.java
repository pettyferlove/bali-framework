package com.github.bali.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "role/index";
    }

}
