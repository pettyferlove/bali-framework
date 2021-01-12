package com.github.bali.auth.web;

import com.github.bali.auth.service.IUserOperateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Petty
 */
@Controller
public class IndexController {

    private final IUserOperateService userOperateService;

    public IndexController(IUserOperateService userOperateService) {
        this.userOperateService = userOperateService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("user", userOperateService.getCurrentUser());
        model.addAttribute("userInfo", userOperateService.getCurrentUserInfo());
        return "index";
    }

}
