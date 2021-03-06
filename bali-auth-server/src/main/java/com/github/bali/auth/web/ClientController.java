package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.ClientCreateResponseVO;
import com.github.bali.auth.domain.vo.ClientDetailsVO;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.auth.service.IClientOperateService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("client")
public class ClientController {

    private final IAuthClientDetailsService authClientDetailsService;

    private final IAuthClientScopeService authClientScopeService;

    private final IClientOperateService clientOperateService;

    public ClientController(IAuthClientDetailsService authClientDetailsService, IAuthClientScopeService authClientScopeService, IClientOperateService clientOperateService) {
        this.authClientDetailsService = authClientDetailsService;
        this.authClientScopeService = authClientScopeService;
        this.clientOperateService = clientOperateService;
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
        model.addAttribute("client", Optional.ofNullable(authClientDetailsService.get(id)).orElseGet(AuthClientDetails::new));
        model.addAttribute("scopes", authClientScopeService.listScopes());
        model.addAttribute("selectedScopes", clientOperateService.selectedScope(id));
        return "client/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "client/view";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public R<ClientCreateResponseVO> create(@RequestBody ClientDetailsVO details) {
        try {
            return new R<>(clientOperateService.create(details));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public R<Boolean> update(@RequestBody ClientDetailsVO details) {
        try {
            return new R<>(clientOperateService.update(details));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> delete(@PathVariable String ids) {
        try {
            return new R<>(clientOperateService.batchDelete(ids));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

}
