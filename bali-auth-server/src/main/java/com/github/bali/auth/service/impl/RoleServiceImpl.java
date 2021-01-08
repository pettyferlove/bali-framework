package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.mapper.RoleMapper;
import com.github.bali.auth.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public IPage<Role> page(Role role, Page<Role> page) {
        return this.page(page, Wrappers.lambdaQuery(role).orderByDesc(Role::getCreateTime));
    }

    @Override
    public Role get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(Role role) {
        role.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        role.setCreateTime(LocalDateTime.now());
        if (this.save(role)) {
            return role.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(Role role) {
        role.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        role.setModifyTime(LocalDateTime.now());
        return this.updateById(role);
    }

}
