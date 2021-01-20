package com.github.bali.auth.provider.token;

import cn.hutool.core.util.StrUtil;
import com.github.bali.security.userdetails.BaliUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
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
            if (StrUtil.isNotEmpty(principal.getOpenId())) {
                response.put("open_id", principal.getOpenId());
            }
            if (StrUtil.isNotEmpty(principal.getUnionId())) {
                response.put("union_id", principal.getUnionId());
            }
            response.put("username", principal.getUsername());
            response.put("status", principal.getStatus());
            response.put("channel", principal.getChannel());
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            response.put("tenant", principal.getTenant());
            return response;
        }
    }
}
