package com.github.bali.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.core.framework.domain.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "client/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        return "client/edit";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        return "client/view";
    }

}
