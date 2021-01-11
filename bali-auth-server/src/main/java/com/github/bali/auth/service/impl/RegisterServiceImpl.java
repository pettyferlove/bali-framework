package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.domain.vo.WeChatUserRegister;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.service.IRegisterService;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.auth.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Pettyfer
 */
@Slf4j
@Service
public class RegisterServiceImpl implements IRegisterService {

    private final IUserService userService;

    private final IUserInfoService userInfoService;

    public RegisterServiceImpl(IUserService userService, IUserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    @Override
    public Boolean registerWeChat(WeChatUserRegister register, String tenantId, String type) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotEmpty(register.getOpenId())) {
            queryWrapper.eq(User::getOpenId, register.getOpenId());
        }
        if (StrUtil.isNotEmpty(register.getUnionId())) {
            queryWrapper.eq(User::getUnionId, register.getUnionId());
        }
        return null;
    }
}
