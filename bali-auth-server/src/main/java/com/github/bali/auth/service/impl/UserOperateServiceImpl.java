package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.UserOperateVO;
import com.github.bali.auth.domain.vo.UserRoleVO;
import com.github.bali.auth.entity.*;
import com.github.bali.auth.service.*;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.core.framework.utils.ConverterUtil;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.constants.UserChannelConstant;
import com.github.bali.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Petty
 */
@Slf4j
@Service
public class UserOperateServiceImpl implements IUserOperateService {

    private final PasswordEncoder passwordEncoder;

    private final IUserService userService;

    private final IUserInfoService userInfoService;

    private final IUserInfoViewService userInfoViewService;

    private final IRoleService roleService;

    private final IUserRoleService userRoleService;

    public UserOperateServiceImpl(PasswordEncoder passwordEncoder, IUserService userService, IUserInfoService userInfoService, IUserInfoViewService userInfoViewService, IRoleService roleService, IUserRoleService userRoleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.userInfoViewService = userInfoViewService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserRoleVO loadUserRole(String userId) {
        List<Role> toBeSelected = new LinkedList<>();
        List<Role> selected = new LinkedList<>();
        List<Role> roles = Optional.ofNullable(roleService.list()).orElseGet(ArrayList::new);
        List<UserRole> userRoles = Optional.ofNullable(userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId))).orElseGet(ArrayList::new);
        List<String> selectedRoleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        roles.forEach(i -> {
            toBeSelected.add(i);
            if (selectedRoleIds.contains(i.getId())) {
                selected.add(i);
            }
        });
        return UserRoleVO.builder().toBeSelected(toBeSelected).selected(selected).build();
    }

    @Override
    public Page<UserInfoView> userInfoPage(UserInfoView userInfoView, Page<UserInfoView> page) {
        LambdaQueryWrapper<UserInfoView> queryWrapper = Wrappers.<UserInfoView>lambdaQuery().orderByDesc(UserInfoView::getCreateTime)
                .ne(UserInfoView::getId, Objects.requireNonNull(SecurityUtil.getUser()).getId());
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
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(SecurityConstant.TENANT_ADMIN_ROLE_ID);
            userRoleService.save(userRole);
        }
        return userId;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean update(UserOperateVO userOperate) {
        List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userOperate.getId()));
        if (userRoles.stream().anyMatch(i -> i.getRoleId().equals(SecurityConstant.SUPER_ADMIN_ROLE_ID))) {
            log.error("attempts to remove the super administrator");
            throw new BaseRuntimeException("警告：你无法删除系统超级管理员");
        }
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
        if (id.equals(SecurityUtil.getUser().getId())) {
            throw new BaseRuntimeException("无法删除你自己");
        }
        List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
        if (userRoles.stream().anyMatch(i -> i.getRoleId().equals(SecurityConstant.SUPER_ADMIN_ROLE_ID))) {
            log.error("attempts to remove the super administrator");
            throw new BaseRuntimeException("警告：你无法删除系统超级管理员");
        }
        userService.delete(id);
        userInfoService.remove(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, id));
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateUserRole(String userId, String roleIds) {
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId).ne(UserRole::getRoleId, SecurityConstant.TENANT_ADMIN_ROLE_ID));
        if (StrUtil.isNotEmpty(roleIds)) {
            String[] ids = roleIds.split(",");
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : ids) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }
        return true;
    }
}
