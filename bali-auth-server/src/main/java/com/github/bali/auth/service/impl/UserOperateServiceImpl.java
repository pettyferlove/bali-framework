package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.ChangePasswordVO;
import com.github.bali.auth.domain.vo.UserOperate;
import com.github.bali.auth.domain.vo.UserRoleVO;
import com.github.bali.auth.entity.*;
import com.github.bali.auth.service.*;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.core.framework.util.ConverterUtils;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.constants.UserChannelType;
import com.github.bali.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        List<Role> roles = Optional.ofNullable(roleService.list(Wrappers.<Role>lambdaQuery()
                .ne(Role::getId, SecurityConstant.SUPER_ADMIN_ROLE_ID)
                .ne(Role::getId, SecurityConstant.TENANT_ADMIN_ROLE_ID)
        )).orElseGet(ArrayList::new);
        List<UserRole> userRoles = Optional.ofNullable(userRoleService.list(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .ne(UserRole::getRoleId, SecurityConstant.SUPER_ADMIN_ROLE_ID)
                .ne(UserRole::getRoleId, SecurityConstant.TENANT_ADMIN_ROLE_ID)
        )).orElseGet(ArrayList::new);
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
    public List<String> loadUserRoleIds(String userId) {
        List<UserRole> userRoles = Optional.ofNullable(userRoleService.list(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .ne(UserRole::getRoleId, SecurityConstant.SUPER_ADMIN_ROLE_ID)
                .ne(UserRole::getRoleId, SecurityConstant.TENANT_ADMIN_ROLE_ID)
        )).orElseGet(ArrayList::new);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public Page<UserInfoView> userInfoPage(UserInfoView userInfoView, Page<UserInfoView> page) {
        LambdaQueryWrapper<UserInfoView> queryWrapper = Wrappers.<UserInfoView>lambdaQuery().orderByDesc(UserInfoView::getCreateTime);
        queryWrapper.likeRight(StrUtil.isNotEmpty(userInfoView.getUserName()), UserInfoView::getUserName, userInfoView.getUserName());
        queryWrapper.likeRight(StrUtil.isNotEmpty(userInfoView.getNickName()), UserInfoView::getNickName, userInfoView.getNickName());
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            queryWrapper.exists("select role_id from uc_user_role where role_id = '" + SecurityConstant.TENANT_ADMIN_ROLE_ID + "' and v_user_info_view.id = uc_user_role.user_id");
        }
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
    public String create(UserOperate userOperate) {
        try {
            User user = new User();
            user.setLoginId(userOperate.getLoginId());
            user.setPassword(passwordEncoder.encode(userOperate.getPassword()));
            user.setUserChannel(UserChannelType.MAINTAINER.getValue());
            user.setStatus(userOperate.getStatus());
            if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
                user.setTenantId(userOperate.getTenantId());
            } else {
                user.setTenantId(null);
            }
            String userId = userService.create(user);
            UserInfo userInfo = ConverterUtils.convert(userOperate, new UserInfo());
            if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
                userInfo.setTenantId(userOperate.getTenantId());
            } else {
                userInfo.setTenantId(null);
            }
            userInfo.setUserId(userId);
            userInfoService.create(userInfo);
            if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(SecurityConstant.TENANT_ADMIN_ROLE_ID);
                userRoleService.save(userRole);
            }
            // 如果创建用户指定了角色则创建用户-角色关联
            if (StrUtil.isNotEmpty(userOperate.getRoleIds())) {
                this.updateUserRole(userId, userOperate.getRoleIds());
            }
            return userId;
        } catch (DuplicateKeyException e) {
            throw new BaseRuntimeException("登录名已存在，请勿重复添加");
        } catch (Exception e) {
            throw new BaseRuntimeException();
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean update(UserOperate userOperate) {
        if (StrUtil.isEmpty(userOperate.getId())) {
            throw new BaseRuntimeException("提醒：修改用户必须指定用户ID");
        }
        if (!userService.get(userOperate.getId()).getUserChannel().equals(UserChannelType.MAINTAINER.getValue())) {
            throw new BaseRuntimeException("提醒：无法修改改用户");
        }
        List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userOperate.getId()));
        if (userRoles.stream().anyMatch(i -> i.getRoleId().equals(SecurityConstant.SUPER_ADMIN_ROLE_ID))) {
            log.error("user:{},attempts to remove the super administrator", SecurityUtil.getUser().getId());
            throw new BaseRuntimeException("警告：你无法修改系统超级管理员");
        }
        User user = new User();
        user.setId(userOperate.getId());
        user.setStatus(userOperate.getStatus());
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            user.setTenantId(userOperate.getTenantId());
        } else {
            user.setTenantId(null);
        }
        userService.update(user);
        UserInfo userInfo = ConverterUtils.convert(userOperate, new UserInfo());
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            userInfo.setTenantId(userOperate.getTenantId());
        } else {
            userInfo.setTenantId(null);
        }
        UserInfo info = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userOperate.getId()));
        userInfo.setId(info.getId());
        userInfoService.update(userInfo);
        // 如果创建用户指定了角色则创建用户-角色关联
        if (StrUtil.isNotEmpty(userOperate.getRoleIds())) {
            this.updateUserRole(userOperate.getId(), userOperate.getRoleIds());
        }
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
            log.error("user:{},attempts to remove the super administrator", SecurityUtil.getUser().getId());
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
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId).ne(UserRole::getRoleId, SecurityConstant.TENANT_ADMIN_ROLE_ID).ne(UserRole::getRoleId, SecurityConstant.SUPER_ADMIN_ROLE_ID));
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

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean resetPassword(String ids, String password) {
        String superAdminId = "";
        try {
            UserRole userRole = userRoleService.getOne(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, SecurityConstant.SUPER_ADMIN_ROLE_ID));
            superAdminId = userRole.getUserId();
        } catch (Exception e) {
            throw new BaseRuntimeException("警告：系统基础数据不完整，请联系管理员");
        }
        if (ids.contains(superAdminId)) {
            log.error("user:{},try to modify the administrator account password", SecurityUtil.getUser().getId());
            throw new BaseRuntimeException("警告：禁止操作管理员账号的密码");
        }
        List<User> users = new ArrayList<>();
        List<User> updateUsers = Arrays.stream(ids.split(",")).map(i -> {
            User user = new User();
            user.setId(i);
            user.setPassword(passwordEncoder.encode(password));
            return user;
        }).collect(Collectors.toList());
        return userService.updateBatchById(updateUsers);
    }

    @Override
    public Boolean changePassword(String id, ChangePasswordVO changePassword) {
        if (!changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
            throw new BaseRuntimeException("新密码和重复密码不一致");
        } else {
            Optional<User> userOptional = Optional.ofNullable(userService.getById(id));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
                    throw new BaseRuntimeException("账号原始密码错误");
                } else {
                    LambdaUpdateWrapper<User> updateWrapper = Wrappers.<User>lambdaUpdate();
                    updateWrapper.eq(User::getId, id);
                    updateWrapper.set(User::getPassword, passwordEncoder.encode(changePassword.getNewPassword()));
                    updateWrapper.set(User::getModifier, id);
                    updateWrapper.set(User::getModifyTime, LocalDateTime.now());
                    userService.update(updateWrapper);
                    return true;
                }
            } else {
                throw new BaseRuntimeException("用户不存在");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean batchDelete(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            this.delete(id);
        }
        return true;
    }
}
