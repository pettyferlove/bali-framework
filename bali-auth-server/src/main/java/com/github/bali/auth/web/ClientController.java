package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("client")
public class ClientController {

    private final IAuthClientDetailsService authClientDetailsService;

    private final IAuthClientScopeService authClientScopeService;

    public ClientController(IAuthClientDetailsService authClientDetailsService, IAuthClientScopeService authClientScopeService) {
        this.authClientDetailsService = authClientDetailsService;
        this.authClientScopeService = authClientScopeService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "client/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public R<IPage<AuthClientDetails>> list(String clientId, int page, int limit) {
        AuthClientDetails clientDetails = new AuthClientDetails();
        clientDetails.setClientId(clientId);
        Page<AuthClientDetails> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        return new R<>(authClientDetailsService.page(clientDetails, pageQuery));
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("scopes", authClientScopeService.list());
        return "client/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("tenant", Optional.ofNullable(authClientDetailsService.get(id)).orElseGet(AuthClientDetails::new));
        model.addAttribute("scopes", authClientScopeService.list());
        return "client/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "client/view";
    }

}
