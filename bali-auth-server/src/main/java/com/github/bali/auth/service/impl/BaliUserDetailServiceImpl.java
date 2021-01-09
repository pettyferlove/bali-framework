package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.entity.UserRole;
import com.github.bali.auth.service.*;
import com.github.bali.security.userdetails.BaliUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Petty
 */
@Slf4j
@Service
public class BaliUserDetailServiceImpl implements OAuth2UserDetailsService {

    private final IUserService userService;

    private final IUserInfoService userInfoService;

    private final IUserRoleService userRoleService;

    private final IRoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public BaliUserDetailServiceImpl(IUserService userService, IUserInfoService userInfoService, IUserRoleService userRoleService, IRoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getLoginId, loginName)));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserInfo userInfo = Optional.ofNullable(userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, user.getId()))).orElseGet(UserInfo::new);
            List<UserRole> userRoles = Optional.ofNullable(userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()))).orElseGet(ArrayList::new);
            List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            List<String> roles = new LinkedList<>();
            if (!roleIds.isEmpty()) {
                List<Role> roleList = Optional.ofNullable(roleService.list(Wrappers.<Role>lambdaQuery().in(Role::getId, roleIds))).orElseGet(LinkedList::new);
                roles = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
            }
            return BaliUserDetails.builder()
                    .id(user.getId())
                    .username(user.getLoginId())
                    .password(user.getPassword())
                    .status(user.getStatus())
                    .nickname(userInfo.getNickName())
                    .roles(roles)
                    .email(userInfo.getEmail())
                    .tenant(user.getTenantId())
                    .avatar(userInfo.getUserAvatar())
                    .build();
        } else {
            throw new RuntimeException("没有找到该用户");
        }
    }
}
