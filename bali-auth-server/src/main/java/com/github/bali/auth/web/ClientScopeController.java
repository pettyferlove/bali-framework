package com.github.bali.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("client/scope")
public class ClientScopeController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "client/scope/index";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "client/scope/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        return "client/scope/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "client/scope/view";
    }

}
