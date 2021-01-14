package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.AuthClientScope;
import com.github.bali.auth.mapper.AuthClientScopeMapper;
import com.github.bali.auth.service.IAuthClientScopeService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public IPage<AuthClientScope> page(AuthClientScope authClientScope, Page<AuthClientScope> page) {
        return this.page(page, Wrappers.lambdaQuery(authClientScope).orderByDesc(AuthClientScope::getCreateTime));
    }

    @Override
    public AuthClientScope get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(AuthClientScope authClientScope) {
        authClientScope.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientScope.setCreateTime(LocalDateTime.now());
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

}
