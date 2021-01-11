package com.github.bali.auth.web;

import com.github.bali.auth.entity.User;
import com.github.bali.auth.service.IUserService;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/**
 * @author Petty
 */
@Controller
public class IndexController {

    private final IUserService userService;

    public IndexController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        User userInfo = userService.get(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        model.addAttribute("user", userInfo);
        return "index";
    }

}
