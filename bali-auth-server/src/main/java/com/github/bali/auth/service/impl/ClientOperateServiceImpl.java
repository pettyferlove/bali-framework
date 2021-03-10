package com.github.bali.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.domain.vo.ClientCreateResponseVO;
import com.github.bali.auth.domain.vo.ClientDetailsVO;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.entity.AuthClientDetailsScope;
import com.github.bali.auth.service.IAuthClientDetailsScopeService;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.auth.service.IClientOperateService;
import com.github.bali.core.framework.utils.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Petty
 */
@Slf4j
@Service
public class ClientOperateServiceImpl implements IClientOperateService {

    private final PasswordEncoder passwordEncoder;

    private final IAuthClientDetailsService authClientDetailsService;

    private final IAuthClientDetailsScopeService authClientDetailsScopeService;

    public ClientOperateServiceImpl(PasswordEncoder passwordEncoder, IAuthClientDetailsService authClientDetailsService, IAuthClientDetailsScopeService authClientDetailsScopeService) {
        this.passwordEncoder = passwordEncoder;
        this.authClientDetailsService = authClientDetailsService;
        this.authClientDetailsScopeService = authClientDetailsScopeService;
    }

    @Override
    public List<String> selectedScope(String id) {
        List<AuthClientDetailsScope> detailsScopes = authClientDetailsScopeService.list(Wrappers.<AuthClientDetailsScope>lambdaQuery().eq(AuthClientDetailsScope::getDetailsId, id));
        return detailsScopes.stream().map(AuthClientDetailsScope::getScopeId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ClientCreateResponseVO create(ClientDetailsVO details) {
        AuthClientDetails clientDetails = ConverterUtil.convert(details, new AuthClientDetails());
        String clientId = IdUtil.simpleUUID();
        String clientSecret = IdUtil.simpleUUID();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(passwordEncoder.encode(clientSecret));
        String id = authClientDetailsService.create(clientDetails);
        if (StrUtil.isNotEmpty(details.getScope())) {
            String[] scopes = details.getScope().split(",");
            List<AuthClientDetailsScope> authClientDetailsScopes = new ArrayList<>();
            for (String scope : scopes) {
                AuthClientDetailsScope authClientDetailsScope = new AuthClientDetailsScope();
                authClientDetailsScope.setDetailsId(id);
                authClientDetailsScope.setScopeId(scope);
                authClientDetailsScopes.add(authClientDetailsScope);
            }
            authClientDetailsScopeService.saveBatch(authClientDetailsScopes);
        }
        return ClientCreateResponseVO.builder().clientId(clientId).clientSecret(clientSecret).build();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean update(ClientDetailsVO details) {
        AuthClientDetails clientDetails = ConverterUtil.convert(details, new AuthClientDetails());
        authClientDetailsScopeService.remove(Wrappers.<AuthClientDetailsScope>lambdaQuery().eq(AuthClientDetailsScope::getDetailsId, details.getId()));
        if (StrUtil.isNotEmpty(details.getScope())) {
            String[] scopes = details.getScope().split(",");
            List<AuthClientDetailsScope> authClientDetailsScopes = new ArrayList<>();
            for (String scope : scopes) {
                AuthClientDetailsScope authClientDetailsScope = new AuthClientDetailsScope();
                authClientDetailsScope.setDetailsId(details.getId());
                authClientDetailsScope.setScopeId(scope);
                authClientDetailsScopes.add(authClientDetailsScope);
            }
            authClientDetailsScopeService.saveBatch(authClientDetailsScopes);
        }
        return authClientDetailsService.update(clientDetails);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delete(String id) {
        authClientDetailsService.delete(id);
        authClientDetailsScopeService.remove(Wrappers.<AuthClientDetailsScope>lambdaQuery().eq(AuthClientDetailsScope::getDetailsId, id));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean batchDelete(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            this.delete(id);
        }
        return true;
    }
}
