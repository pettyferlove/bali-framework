package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.Tenant;
import com.github.bali.auth.mapper.TenantMapper;
import com.github.bali.auth.service.ITenantService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        LambdaQueryWrapper<Tenant> queryWrapper = Wrappers.<Tenant>lambdaQuery().orderByDesc(Tenant::getCreateTime);
        if (StrUtil.isNotEmpty(tenant.getTenantName())) {
            queryWrapper.likeRight(Tenant::getTenantName, tenant.getTenantName());
        }
        return this.page(page, queryWrapper);
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
        tenant.setTenantId(IdWorker.getIdStr());
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

}
