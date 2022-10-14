package com.github.bali.auth.provider.authentication;

import com.github.bali.auth.provider.service.impl.BaliUserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author Pettyfer
 * @version 1.0
 * @description: 微信登录认证
 * @date 2022/1/21 9:42
 */
@Slf4j
@Component
public class WeChatUnionIdAuthenticationProvider implements AuthenticationProvider {

    private final BaliUserDetailServiceImpl userDetailsService;

    public WeChatUnionIdAuthenticationProvider(BaliUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByWeChatUnionId(principal);
        return new WeChatOpenIdAuthenticationToken(userDetails, "N/A", userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WeChatUnionIdAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
