package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.UserOperateVO;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.core.framework.domain.vo.R;
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

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public R<String> create(@RequestBody UserOperateVO user) {
        try {
            return new R<>(userOperateService.create(user));
        } catch (Exception e) {
            return new R<>( null, e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public R<Boolean> update(@RequestBody UserOperateVO user) {
        try {
            return new R<>(userOperateService.update(user));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> delete(@PathVariable String id) {
        try {
            return new R<>(userOperateService.delete(id));
        } catch (Exception e) {
            return new R<>(false, e.getMessage());
        }
    }

}
