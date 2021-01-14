package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.AuthClientScope;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("client/scope")
public class ClientScopeController {

    private final IAuthClientScopeService clientScopeService;

    public ClientScopeController(IAuthClientScopeService clientScopeService) {
        this.clientScopeService = clientScopeService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "client/scope/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public R<IPage<AuthClientScope>> list(int page, int limit) {
        Page<AuthClientScope> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        return new R<>(clientScopeService.page(new AuthClientScope(), pageQuery));
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
