package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.UserOperateVO;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.auth.service.IUserInfoViewService;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.auth.service.IUserService;
import com.github.bali.core.framework.utils.ConverterUtil;
import com.github.bali.security.constants.UserChannelConstant;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Petty
 */
@Service
public class UserOperateServiceImpl implements IUserOperateService {

    private final PasswordEncoder passwordEncoder;

    private final IUserService userService;

    private final IUserInfoService userInfoService;

    private final IUserInfoViewService userInfoViewService;

    public UserOperateServiceImpl(PasswordEncoder passwordEncoder, IUserService userService, IUserInfoService userInfoService, IUserInfoViewService userInfoViewService) {
        this.passwordEncoder = passwordEncoder;
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
    public User getUser(String userId) {
        return userService.get(userId);
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
    }

    @Override
    public User getCurrentUser() {
        return userService.get(Objects.requireNonNull(SecurityUtil.getUser()).getId());
    }

    @Override
    public UserInfo getCurrentUserInfo() {
        return userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, Objects.requireNonNull(SecurityUtil.getUser()).getId()));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String create(UserOperateVO userOperate) {
        User user = new User();
        user.setLoginId(userOperate.getLoginId());
        user.setPassword(passwordEncoder.encode(userOperate.getPassword()));
        user.setUserChannel(UserChannelConstant.WEB);
        user.setStatus(userOperate.getStatus());
        user.setTenantId(userOperate.getTenantId());
        String userId = userService.create(user);
        UserInfo userInfo = ConverterUtil.convert(userOperate, new UserInfo());
        userInfo.setUserId(userId);
        userInfoService.create(userInfo);
        return userId;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean update(UserOperateVO userOperate) {
        User user = new User();
        user.setId(userOperate.getId());
        user.setStatus(userOperate.getStatus());
        user.setTenantId(userOperate.getTenantId());
        userService.update(user);
        UserInfo userInfo = ConverterUtil.convert(userOperate, new UserInfo());
        UserInfo info = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userOperate.getId()));
        userInfo.setId(info.getId());
        userInfoService.update(userInfo);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delete(String id) {
        userService.delete(id);
        userInfoService.remove(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, id));
        return true;
    }
}
