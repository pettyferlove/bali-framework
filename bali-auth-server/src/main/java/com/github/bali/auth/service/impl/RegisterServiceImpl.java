package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.domain.dto.BasicAuth;
import com.github.bali.auth.domain.vo.WeChatUserRegister;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.service.IRegisterService;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.auth.service.IUserService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.constants.UserChannelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    @Transactional(rollbackFor = Throwable.class)
    public Boolean registerWeChat(WeChatUserRegister register, BasicAuth auth, UserChannelType type) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotEmpty(register.getOpenId())) {
            queryWrapper.eq(User::getOpenId, register.getOpenId());
        }
        if (StrUtil.isNotEmpty(register.getUnionId())) {
            queryWrapper.eq(User::getUnionId, register.getUnionId());
        }
        int count = userService.count(queryWrapper);
        if (count > 0) {
            throw new BaseRuntimeException("存在同样的数据，请检查后提交");
        } else {
            User user = new User();
            user.setOpenId(register.getOpenId());
            user.setUnionId(register.getUnionId());
            user.setTenantId(auth.getTenantId());
            user.setClientId(auth.getClientId());
            user.setUserChannel(type.getValue());
            user.setStatus(SecurityConstant.STATUS_NORMAL);
            user.setCreator("anonymous");
            user.setCreateTime(LocalDateTime.now());
            userService.save(user);
            String userId = user.getId();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setClientId(auth.getClientId());
            userInfo.setTenantId(auth.getTenantId());
            userInfo.setUserAvatar(register.getAvatarUrl());
            userInfo.setNickName(register.getNickName());
            userInfo.setUserSex(register.getGender());
            userInfo.setCreator("anonymous");
            userInfo.setCreateTime(LocalDateTime.now());
            userInfoService.save(userInfo);
        }
        return true;
    }
}
