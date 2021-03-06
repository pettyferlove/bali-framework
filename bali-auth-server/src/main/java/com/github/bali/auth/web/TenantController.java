package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.TenantDictVO;
import com.github.bali.auth.entity.Tenant;
import com.github.bali.auth.service.ITenantService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("tenant")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class TenantController {

    private final ITenantService tenantService;

    public TenantController(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "tenant/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public R<IPage<Tenant>> list(String tenantName, int page, int limit) {
        Tenant tenant = new Tenant();
        tenant.setTenantName(tenantName);
        Page<Tenant> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        return new R<>(tenantService.page(tenant, pageQuery));
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "tenant/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("tenant", Optional.ofNullable(tenantService.get(id)).orElseGet(Tenant::new));
        return "tenant/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "tenant/view";
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public R<String> create(@RequestBody Tenant tenant) {
        try {
            return new R<>(tenantService.create(tenant));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public R<Boolean> update(@RequestBody Tenant tenant) {
        try {
            return new R<>(tenantService.update(tenant));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> delete(@PathVariable String id) {
        try {
            return new R<>(tenantService.delete(id));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    public R<List<TenantDictVO>> all() {
        return new R<>(tenantService.dict());
    }

}
