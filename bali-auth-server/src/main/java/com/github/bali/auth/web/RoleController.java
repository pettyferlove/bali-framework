package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("role")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "role/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public R<IPage<Role>> list(int page, int limit) {
        Role role = new Role();
        Page<Role> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
       return new R<>(roleService.page(role, pageQuery));
    }

}
