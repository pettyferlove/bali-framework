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
            response.put(USERNAME, authentication.getName());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
                response.put("permissions", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }
            response.put("iss", "http://127.0.0.1:9000");
            return response;
        }
    }
}
