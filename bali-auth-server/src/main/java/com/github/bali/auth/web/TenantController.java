package com.github.bali.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("tenant")
public class TenantController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "tenant/index";
    }

}
