package com.github.bali.security.access;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @author Pettyfer
 */
public class BaliPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        /*SysUser user = (SysUser)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的权限
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 遍历用户权限进行判定
        for(GrantedAuthority authority : authorities) {
            UrlGrantedAuthority urlGrantedAuthority = (UrlGrantedAuthority) authority;
            String permissionUrl = urlGrantedAuthority.getPermissionUrl();
            // 如果访问的Url和权限用户符合的话，返回true
            if(targetDomainObject.equals(permissionUrl)) {
                return true;
            }
        }*/
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
