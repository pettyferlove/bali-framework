package com.github.bali.security.userdetails;

import cn.hutool.core.util.StrUtil;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.constants.UserChannelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author Petty
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaliUserDetails implements UserDetails {

    private static final long serialVersionUID = 1254385573535663771L;

    /**
     * 用户ID
     */
    private String id;

    /**
     * 第三方开放ID
     */
    private String openId;

    /**
     * 第三方机构ID
     */
    private String unionId;

    /**
     * 用户登录用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户渠道
     */
    @Builder.Default
    private String channel = UserChannelType.WEB.getValue();

    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 用户状态
     *
     * @see com.github.bali.security.constants.SecurityConstant
     */
    private Integer status;
    /**
     * 所属租户ID
     */
    private String tenant;
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (String role : roles) {
            if (StrUtil.isNotEmpty(role)) {
                String r = role.toUpperCase();
                if(!r.contains(SecurityConstant.ROLE_PREFIX)){
                    authoritySet.add(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + r));
                } else {
                    authoritySet.add(new SimpleGrantedAuthority(r));
                }
            }
        }
        authoritySet.add(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + SecurityConstant.BASE_ROLE));
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !Objects.equals(SecurityConstant.STATUS_INVALID, status);
    }

    /**
     * 用户是否锁定
     *
     * @return True 表示锁定，False 表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(SecurityConstant.STATUS_LOCK, status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !Objects.equals(SecurityConstant.STATUS_CREDENTIALS_EXPIRED, status);
    }

    /**
     * 用户是否有效
     *
     * @return True有效，False无效
     */
    @Override
    public boolean isEnabled() {
        switch (status) {
            // 将无效用户、过期用户交给后续的检查器处理
            case SecurityConstant.STATUS_NORMAL:
            case SecurityConstant.STATUS_INVALID:
            case SecurityConstant.STATUS_CREDENTIALS_EXPIRED:
                return true;
            default:
                return false;
        }
    }
}
