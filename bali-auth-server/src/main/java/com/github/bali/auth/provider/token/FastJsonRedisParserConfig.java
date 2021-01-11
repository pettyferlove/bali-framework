package com.github.bali.auth.provider.token;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

/**
 * @author Pettyfer
 */
public class FastJsonRedisParserConfig {

    public static final ParserConfig DEFAULT_CONFIG = ParserConfig.getGlobalInstance();

    static {
        init();
    }

    protected static void init() {
        //自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        DEFAULT_CONFIG.setAutoTypeSupport(true);
        DEFAULT_CONFIG.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOAuth2RefreshTokenDeserializer());
        DEFAULT_CONFIG.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationDeserializer());
        DEFAULT_CONFIG.addAccept("org.springframework.security.oauth2.provider.");
        DEFAULT_CONFIG.addAccept("org.springframework.security.oauth2.provider.client");

        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication",
                OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails",
                BaseClientDetails.class);
        TypeUtils.addMapping("org.springframework.security.web.savedrequest.DefaultSavedRequest",
                DefaultSavedRequest.class);
        TypeUtils.addMapping("org.springframework.security.authentication.InternalAuthenticationServiceException",
                InternalAuthenticationServiceException.class);
        TypeUtils.addMapping("org.springframework.security.authentication.BadCredentialsException",
                BadCredentialsException.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.AuthorizationRequest",
                AuthorizationRequest.class);
    }

}
