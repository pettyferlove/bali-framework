package com.github.bali.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.entity.*;
import com.github.bali.auth.provider.error.TenantException;
import com.github.bali.auth.provider.error.UserTenantException;
import com.github.bali.auth.service.*;
import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final IAuthClientDetailsService authClientDetailsService;

    private final IUserService userService;

    private final IUserInfoService userInfoService;

    private final IUserRoleService userRoleService;

    private final IRoleService roleService;

    private final ITenantService tenantService;

    public BaliUserDetailServiceImpl(IAuthClientDetailsService authClientDetailsService, IUserService userService, IUserInfoService userInfoService, IUserRoleService userRoleService, IRoleService roleService, ITenantService tenantService) {
        this.authClientDetailsService = authClientDetailsService;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.tenantService = tenantService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getLoginId, loginName)));
        if (userOptional.isPresent()) {
            return transform(userOptional.get());
        } else {
            throw new UsernameNotFoundException("???????????????");
        }
    }


    public UserDetails loadUserByWeChatOpenId(String openId) {
        Optional<User> userOptional = Optional.ofNullable(userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId)));
        if (userOptional.isPresent()) {
            return transform(userOptional.get());
        } else {
            throw new UsernameNotFoundException("???????????????");
        }
    }

    /**
     * ????????????UnionID??????????????????????????????
     *
     * @param unionId UnionID
     * @return UserDetails
     */
    public UserDetails loadUserByWeChatUnionId(String unionId) {
        Optional<User> userOptional = Optional.ofNullable(userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUnionId, unionId)));
        if (userOptional.isPresent()) {
            return transform(userOptional.get());
        } else {
            throw new UsernameNotFoundException("???????????????");
        }
    }

    private BaliUserDetails transform(User user) {
        UserInfo userInfo = Optional.ofNullable(userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, user.getId()))).orElseGet(UserInfo::new);
        List<UserRole> userRoles = Optional.ofNullable(userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()))).orElseGet(ArrayList::new);
        List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<String> roles = new LinkedList<>();
        if (!roleIds.isEmpty()) {
            List<Role> roleList = Optional.ofNullable(roleService.list(Wrappers.<Role>lambdaQuery().in(Role::getId, roleIds))).orElseGet(LinkedList::new);
            roles = roleList.stream().map(Role::getRole).collect(Collectors.toList());
        }
        if (StrUtil.isEmpty(user.getTenantId())) {
            throw new UserTenantException("??????????????????????????????????????????");
        } else {
            try {
                Tenant tenant = tenantService.getOne(Wrappers.<Tenant>lambdaQuery().eq(Tenant::getTenantId, user.getTenantId()));
                if (tenant.getStatus() == 0) {
                    throw new TenantException("???????????????????????????");
                }
            } catch (Exception e) {
                throw new TenantException("???????????????????????????");
            }
        }

        // ?????????Basic???????????????????????????????????????????????????????????????????????????????????????????????????
        Authentication authentication = SecurityUtil.getAuthentication();
        // ??????Null?????????client_id
        if (ObjectUtil.isNotNull(authentication) && !(authentication.getPrincipal() instanceof BaliUserDetails)) {
            String clientId = authentication.getName();
            AuthClientDetails clientDetails = authClientDetailsService.getOne(Wrappers.<AuthClientDetails>lambdaQuery().eq(AuthClientDetails::getClientId, clientId));
            if (!user.getTenantId().equals(clientDetails.getTenantId())) {
                throw new UsernameNotFoundException("???????????????");
            }
        }

        return BaliUserDetails.builder()
                .id(user.getId())
                .openId(user.getOpenId())
                .unionId(user.getUnionId())
                .username(user.getLoginId())
                .password(user.getPassword())
                .channel(user.getUserChannel())
                .status(user.getStatus())
                .nickname(StrUtil.isNotEmpty(userInfo.getUserName()) ? userInfo.getUserName() : userInfo.getNickName())
                .roles(roles)
                .email(userInfo.getEmail())
                .tenant(user.getTenantId())
                .avatar(userInfo.getUserAvatar())
                .build();
    }

}
