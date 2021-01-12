package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public R<IPage<Role>> list(String roleName, int page, int limit) {
        Role role = new Role();
        role.setRoleName(roleName);
        Page<Role> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        return new R<>(roleService.page(role, pageQuery));
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "role/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("role", Optional.ofNullable(roleService.get(id)).orElseGet(Role::new));
        return "role/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "role/view";
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public R<String> create(@RequestBody Role role) {
        try {
            return new R<>(roleService.create(role));
        } catch (Exception e) {
            throw new BaseRuntimeException("新增角色失败");
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public R<Boolean> update(@RequestBody Role role) {
        try {
            return new R<>(roleService.update(role));
        } catch (Exception e) {
            throw new BaseRuntimeException("新增角色失败");
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> delete(@PathVariable String id) {
        try {
            return new R<>(roleService.delete(id));
        } catch (Exception e) {
            throw new BaseRuntimeException("删除角色失败");
        }
    }

}
