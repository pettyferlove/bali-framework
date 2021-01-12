package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.service.IUserInfoService;
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

    public UserOperateServiceImpl(IUserService userService, IUserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
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
