package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.ChangePasswordVO;
import com.github.bali.auth.domain.vo.UserOperate;
import com.github.bali.auth.domain.vo.UserRoleVO;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Petty
 */
@Controller
@RequestMapping("user")
public class UserController {

    private final IUserOperateService userOperateService;

    public UserController(IUserOperateService userOperateService) {
        this.userOperateService = userOperateService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "user/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public R<IPage<UserInfoView>> list(UserInfoView userInfo, int page, int limit) {
        Page<UserInfoView> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        return new R<>(userOperateService.userInfoPage(userInfo, pageQuery));
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "user/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("user", userOperateService.getUser(id));
        model.addAttribute("userInfo", userOperateService.getUserInfo(id));
        return "user/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "user/view";
    }

    @RequestMapping("/distribution-roles/{id}")
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public String distributionRoles(@PathVariable String id, Model model) {
        model.addAttribute("userId", id);
        return "user/distribution-roles";
    }

    @RequestMapping(value = "{id}/role", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public R<UserRoleVO> loadUserRole(@PathVariable String id) {
        return new R<>(userOperateService.loadUserRole(id));
    }

    @RequestMapping(value = "{id}/role", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public R<Boolean> updateUserRole(@PathVariable String id, @RequestParam(defaultValue = "") String roleIds) {
        try {
            return new R<>(userOperateService.updateUserRole(id, roleIds));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public R<String> create(@RequestBody UserOperate user) {
        try {
            return new R<>(userOperateService.create(user));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public R<Boolean> update(@RequestBody UserOperate user) {
        try {
            return new R<>(userOperateService.update(user));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> delete(@PathVariable String ids) {
        try {
            return new R<>(userOperateService.batchDelete(ids));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "change/password", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> changePassword(@RequestBody ChangePasswordVO changePassword) {
        try {
            return new R<>(userOperateService.changePassword(SecurityUtil.getUser().getId(), changePassword));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "reset/password", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('TENANT_ADMIN', 'SUPER_ADMIN')")
    @ResponseBody
    public R<Boolean> resetPassword(@RequestParam(defaultValue = "") String ids, @RequestParam String password) {
        try {
            return new R<>(userOperateService.resetPassword(ids, password));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

}
