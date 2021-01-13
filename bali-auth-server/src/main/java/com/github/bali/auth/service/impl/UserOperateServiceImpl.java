package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.*;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.auth.service.IUserInfoViewService;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.auth.service.IUserService;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Petty
 */
@Service
public class UserOperateServiceImpl implements IUserOperateService {

    private final IUserService userService;

    private final IUserInfoService userInfoService;

    private final IUserInfoViewService userInfoViewService;

    public UserOperateServiceImpl(IUserService userService, IUserInfoService userInfoService, IUserInfoViewService userInfoViewService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.userInfoViewService = userInfoViewService;
    }

    @Override
    public Page<UserInfoView> userInfoPage(UserInfoView userInfoView, Page<UserInfoView> page) {
        LambdaQueryWrapper<UserInfoView> queryWrapper = Wrappers.<UserInfoView>lambdaQuery().orderByDesc(UserInfoView::getCreateTime);
        queryWrapper.likeRight(StrUtil.isNotEmpty(userInfoView.getUserName()), UserInfoView::getUserName, userInfoView.getUserName());
        queryWrapper.likeRight(StrUtil.isNotEmpty(userInfoView.getNickName()), UserInfoView::getNickName, userInfoView.getNickName());
        return userInfoViewService.page(page, queryWrapper);
    }

    @Override
    public User getCurrentUser() {
        return userService.get(Objects.requireNonNull(SecurityUtil.getUser()).getId());
    }

    @Override
    public UserInfo getCurrentUserInfo() {
        return userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, Objects.requireNonNull(SecurityUtil.getUser()).getId()));
    }
}
