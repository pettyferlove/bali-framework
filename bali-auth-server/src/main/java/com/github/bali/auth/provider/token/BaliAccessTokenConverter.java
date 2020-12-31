package com.github.bali.auth.provider.token;

import com.github.bali.auth.userdetails.BaliUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Pettyfer
 */
@Slf4j
public class BaliAccessTokenConverter extends DefaultAccessTokenConverter {

    public BaliAccessTokenConverter() {
        // 注入自定义用户认证转换器
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }

    public static class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            Map<String, Object> response = new LinkedHashMap<String, Object>();
            BaliUserDetails principal = (BaliUserDetails) authentication.getPrincipal();
            response.put("id", principal.getId());
            response.put("username", principal.getUsername());
            response.put("nickname", principal.getNickname());
            response.put("avatar", principal.getAvatar());
            response.put("email", principal.getEmail());
            response.put("tenant", principal.getTenant());
            response.put("roles", principal.getRoles());
            response.put("permissions", principal.getPermissions());
            response.put("iss", "http://127.0.0.1:9000");
            return response;
        }
    }
}
