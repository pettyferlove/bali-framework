package com.github.bali.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.AuthClientDetailsScope;
import com.github.bali.auth.mapper.AuthClientDetailsScopeMapper;
import com.github.bali.auth.service.IAuthClientDetailsScopeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-14
 */
@Service
public class AuthClientDetailsScopeServiceImpl extends ServiceImpl<AuthClientDetailsScopeMapper, AuthClientDetailsScope> implements IAuthClientDetailsScopeService {

    @Override
    public IPage<AuthClientDetailsScope> page(AuthClientDetailsScope authClientDetailsScope, Page<AuthClientDetailsScope> page) {
        return this.page(page, Wrappers.lambdaQuery(authClientDetailsScope).orderByDesc(AuthClientDetailsScope::getCreateTime));
    }

    @Override
    public AuthClientDetailsScope get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(AuthClientDetailsScope authClientDetailsScope) {
        authClientDetailsScope.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientDetailsScope.setCreateTime(LocalDateTime.now());
        if (this.save(authClientDetailsScope)) {
            return authClientDetailsScope.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(AuthClientDetailsScope authClientDetailsScope) {
        authClientDetailsScope.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        authClientDetailsScope.setModifyTime(LocalDateTime.now());
        return this.updateById(authClientDetailsScope);
    }

}
