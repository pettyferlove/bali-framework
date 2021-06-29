package com.github.bali.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.entity.UserRole;
import com.github.bali.auth.mapper.RoleMapper;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.auth.service.IUserRoleService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final IUserRoleService userRoleService;

    public RoleServiceImpl(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public IPage<Role> page(Role role, Page<Role> page) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.<Role>lambdaQuery().orderByDesc(Role::getCreateTime);
        if (StrUtil.isNotEmpty(role.getRoleName())) {
            queryWrapper.likeRight(Role::getRoleName, role.getRoleName());
        }
        queryWrapper.ne(Role::getRole, SecurityConstant.SUPER_ADMIN_ROLE);
        return this.page(page, queryWrapper);
    }

    @Override
    public Role get(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delete(String id) {
        Role role = Optional.ofNullable(this.get(id)).orElseGet(Role::new);
        if (SecurityConstant.SUPER_ADMIN_ROLE.equals(role.getRole())) {
            log.error("user:{},attempts to remove the super administrator role", SecurityUtil.getUser().getId());
            throw new BaseRuntimeException("警告：你无法删除超级管理员角色");
        }
        if (SecurityConstant.TENANT_ADMIN_ROLE.equals(role.getRole())) {
            log.error("user:{},attempts to remove the tenant administrator role", SecurityUtil.getUser().getId());
            throw new BaseRuntimeException("警告：你无法删除租户管理员角色");
        }
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, id));
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
        Role r = Optional.ofNullable(this.get(role.getId())).orElseGet(Role::new);
        if (SecurityConstant.SUPER_ADMIN_ROLE.equals(r.getRole())) {
            log.error("user:{},attempts to update the super administrator role", SecurityUtil.getUser().getId());
            throw new BaseRuntimeException("警告：你无法修改超级管理员角色");
        }
        if (SecurityConstant.TENANT_ADMIN_ROLE.equals(r.getRole())) {
            log.error("user:{},attempts to update the tenant administrator role", SecurityUtil.getUser().getId());
            throw new BaseRuntimeException("警告：你无法修改租户管理员角色");
        }
        Role updateRole = new Role();
        updateRole.setId(role.getId());
        updateRole.setRoleName(role.getRoleName());
        updateRole.setDescription(role.getDescription());
        updateRole.setStatus(role.getStatus());
        updateRole.setSort(role.getSort());
        updateRole.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        updateRole.setModifyTime(LocalDateTime.now());
        return this.updateById(updateRole);
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
