package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.AuthClientDetailsScope;
import com.github.bali.auth.entity.AuthClientScope;
import com.github.bali.auth.mapper.AuthClientScopeMapper;
import com.github.bali.auth.service.IAuthClientDetailsScopeService;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 客户端域 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-14
 */
@Service
public class AuthClientScopeServiceImpl extends ServiceImpl<AuthClientScopeMapper, AuthClientScope> implements IAuthClientScopeService {

    private final IAuthClientDetailsScopeService authClientDetailsScopeService;

    public AuthClientScopeServiceImpl(IAuthClientDetailsScopeService authClientDetailsScopeService) {
        this.authClientDetailsScopeService = authClientDetailsScopeService;
    }

    @Override
    public IPage<AuthClientScope> page(AuthClientScope authClientScope, Page<AuthClientScope> page) {
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            authClientScope.setTenantId(Objects.requireNonNull(SecurityUtil.getUser()).getTenant());
        }
        return this.page(page, Wrappers.lambdaQuery(authClientScope).orderByDesc(AuthClientScope::getCreateTime));
    }

    @Override
    public AuthClientScope get(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delete(String id) {
        authClientDetailsScopeService.remove(Wrappers.<AuthClientDetailsScope>lambdaQuery().eq(AuthClientDetailsScope::getScopeId, id));
        return this.removeById(id);
    }

    @Override
    public String create(AuthClientScope authClientScope) {
        authClientScope.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientScope.setCreateTime(LocalDateTime.now());
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            authClientScope.setTenantId(Objects.requireNonNull(SecurityUtil.getUser()).getTenant());
        }
        if (this.save(authClientScope)) {
            return authClientScope.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(AuthClientScope authClientScope) {
        authClientScope.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientScope.setModifyTime(LocalDateTime.now());
        return this.updateById(authClientScope);
    }

    @Override
    public List<AuthClientScope> listScopes() {
        LambdaQueryWrapper<AuthClientScope> queryWrapper = Wrappers.<AuthClientScope>lambdaQuery();
        if (SecurityUtil.getRoles().contains(SecurityConstant.SUPER_ADMIN_ROLE)) {
            queryWrapper.eq(AuthClientScope::getTenantId, Objects.requireNonNull(SecurityUtil.getUser()).getTenant());
        }
        return this.list(queryWrapper);
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
