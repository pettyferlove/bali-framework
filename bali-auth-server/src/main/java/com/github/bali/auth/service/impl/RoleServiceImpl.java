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
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final IUserRoleService userRoleService;

    public RoleServiceImpl(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public IPage<Role> page(Role role, Page<Role> page) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.<Role>lambdaQuery().orderByDesc(Role::getCreateTime);
        if(StrUtil.isNotEmpty(role.getRoleName())){
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
            log.error("attempts to remove the super administrator role");
            throw new BaseRuntimeException("警告：你无法删除超级管理员角色");
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
            log.error("attempts to remove the super administrator role");
            throw new BaseRuntimeException("警告：你无法修改超级管理员角色");
        }
        role.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        role.setModifyTime(LocalDateTime.now());
        return this.updateById(role);
    }

}
