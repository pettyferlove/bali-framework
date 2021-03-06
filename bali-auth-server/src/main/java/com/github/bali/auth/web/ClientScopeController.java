package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.AuthClientScope;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        model.addAttribute("scope", Optional.ofNullable(clientScopeService.get(id)).orElseGet(AuthClientScope::new));
        return "client/scope/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "client/scope/view";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public R<String> create(@RequestBody AuthClientScope scope) {
        try {
            return new R<>(clientScopeService.create(scope));
        } catch (Exception e) {
            return new R<>( null, e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public R<Boolean> update(@RequestBody AuthClientScope scope) {
        try {
            return new R<>(clientScopeService.update(scope));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> delete(@PathVariable String ids) {
        try {
            return new R<>(clientScopeService.batchDelete(ids));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

}
