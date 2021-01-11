package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Tenant;
import com.github.bali.auth.mapper.TenantMapper;
import com.github.bali.auth.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 租户信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Override
    public IPage<Tenant> page(Tenant tenant, Page<Tenant> page) {
        return this.page(page, Wrappers.lambdaQuery(tenant).orderByDesc(Tenant::getCreateTime));
    }

    @Override
    public Tenant get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(Tenant tenant) {
        tenant.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        tenant.setCreateTime(LocalDateTime.now());
        if (this.save(tenant)) {
            return tenant.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(Tenant tenant) {
        tenant.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        tenant.setModifyTime(LocalDateTime.now());
        return this.updateById(tenant);
    }

    @Override
    public List<Tenant> all() {
        return null;
    }

    @Override
    public String create(String userId, Tenant tenant) {
        return null;
    }

    @Override
    public Boolean update(String userId, Tenant tenant) {
        return null;
    }

    @Override
    public Boolean changeStatus(String userId, Tenant tenant) {
        return null;
    }

    @Override
    public Boolean check(String tenantId) {
        return null;
    }

    @Override
    public Boolean checkTenantStatus(String tenantId) {
        return null;
    }

}
