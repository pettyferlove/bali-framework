package com.github.bali.auth.granter;

import cn.hutool.core.util.StrUtil;
import com.github.bali.auth.service.impl.BaliUserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Petty
 */
@Slf4j
@Component
public class WeChatTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "wechat";

    private final String OPEN_ID_KEY = "open_id";
    private final String UNION_ID_KEY = "union_id";

    private final OAuth2RequestFactory requestFactory;

    private final BaliUserDetailServiceImpl userDetailsService;

    protected WeChatTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, BaliUserDetailServiceImpl userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.requestFactory = requestFactory;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        UserDetails userDetails = null;
        try {
            if (parameters.containsKey(UNION_ID_KEY)) {
                String unionId = parameters.get(UNION_ID_KEY);
                if (StrUtil.isNotBlank(unionId)) {
                    userDetails = userDetailsService.loadUserByWeChatUnionId(unionId);
                }
            } else if (parameters.containsKey(OPEN_ID_KEY)) {
                String openId = parameters.get(OPEN_ID_KEY);
                if (StrUtil.isNotBlank(openId)) {
                    userDetails = userDetailsService.loadUserByWeChatOpenId(openId);
                }
            }
        } catch (Exception e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (userDetails == null) {
            throw new OAuth2Exception("请求参数异常");
        }
        AbstractAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        userAuth.setDetails(parameters);
        OAuth2Request authRequest = getRequestFactory().createOAuth2Request(client, tokenRequest);
        OAuth2Authentication authentication = new OAuth2Authentication(authRequest, userAuth);
        return authentication;
    }

}
