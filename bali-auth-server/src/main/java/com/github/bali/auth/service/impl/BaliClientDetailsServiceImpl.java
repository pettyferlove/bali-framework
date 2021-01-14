package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.entity.AuthClientDetailsScope;
import com.github.bali.auth.entity.AuthClientScope;
import com.github.bali.auth.service.IAuthClientDetailsScopeService;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.auth.service.OAuth2ClientDetailsService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Petty
 */
@Slf4j
@Service
public class BaliClientDetailsServiceImpl implements OAuth2ClientDetailsService {

    private final IAuthClientDetailsService authClientDetailsService;

    private final IAuthClientDetailsScopeService authClientDetailsScopeService;

    private final IAuthClientScopeService authClientScopeService;

    public BaliClientDetailsServiceImpl(IAuthClientDetailsService authClientDetailsService, IAuthClientDetailsScopeService authClientDetailsScopeService, IAuthClientScopeService authClientScopeService) {
        this.authClientDetailsService = authClientDetailsService;
        this.authClientDetailsScopeService = authClientDetailsScopeService;
        this.authClientScopeService = authClientScopeService;
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        AuthClientDetails details = authClientDetailsService.getOne(Wrappers.<AuthClientDetails>lambdaQuery().eq(AuthClientDetails::getClientId, clientId));
        List<AuthClientDetailsScope> list = authClientDetailsScopeService.list(Wrappers.<AuthClientDetailsScope>lambdaQuery().eq(AuthClientDetailsScope::getDetailsId, details.getId()));
        List<String> scope = new LinkedList<>();
        List<String> autoApproveScope = new LinkedList<>();
        List<String> scopeIds = new ArrayList<>();
        for (AuthClientDetailsScope detailsScope : list) {
            scopeIds.add(detailsScope.getScopeId());
        }
        if (!scopeIds.isEmpty()) {
            List<AuthClientScope> clientScopes = authClientScopeService.list(Wrappers.<AuthClientScope>lambdaQuery().in(AuthClientScope::getId, scopeIds));
            clientScopes.forEach(i -> {
                scope.add(i.getScope());
                if (i.getAuto()) {
                    autoApproveScope.add(i.getScope());
                }
            });
        }

        Preconditions.checkNotNull(details, "client is not registered");
        BaseClientDetails baseClientDetails = new BaseClientDetails(details.getClientId(), details.getResourceIds(),
                StrUtil.join(",", scope), details.getAuthorizedGrantTypes(), details.getAuthorities(), details.getWebServerRedirectUri());
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
        baseClientDetails.setAutoApproveScopes(autoApproveScope);
        return baseClientDetails;
    }
}
