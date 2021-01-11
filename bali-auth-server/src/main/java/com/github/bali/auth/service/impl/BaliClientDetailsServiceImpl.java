package com.github.bali.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.auth.service.OAuth2ClientDetailsService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Petty
 */
@Slf4j
@Service
public class BaliClientDetailsServiceImpl implements OAuth2ClientDetailsService {

    private final IAuthClientDetailsService authClientDetailsService;

    public BaliClientDetailsServiceImpl(IAuthClientDetailsService authClientDetailsService) {
        this.authClientDetailsService = authClientDetailsService;
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        AuthClientDetails details = authClientDetailsService.getOne(Wrappers.<AuthClientDetails>lambdaQuery().eq(AuthClientDetails::getClientId, clientId));
        Preconditions.checkNotNull(details, "client is not registered");
        BaseClientDetails baseClientDetails = new BaseClientDetails(details.getClientId(), details.getResourceIds(),
                details.getScope(), details.getAuthorizedGrantTypes(), details.getAuthorities(), details.getWebServerRedirectUri());
        baseClientDetails.setClientSecret(details.getClientSecret());
        if (details.getAccessTokenValidity() != null) {
            baseClientDetails.setAccessTokenValiditySeconds(details.getAccessTokenValidity());
        }
        if (details.getRefreshTokenValidity() != null) {
            baseClientDetails.setRefreshTokenValiditySeconds(details.getRefreshTokenValidity());
        }


        String json = details.getAdditionalInformation();
        if (json != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> additionalInformation = JSON.parseObject(json).getInnerMap();
                baseClientDetails.setAdditionalInformation(additionalInformation);
            } catch (Exception e) {
                log.warn("Could not decode JSON for additional information: " + baseClientDetails, e);
            }
        }
        String scopes = details.getAutoApprove();
        if (scopes != null) {
            baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
        }
        return baseClientDetails;
    }
}
