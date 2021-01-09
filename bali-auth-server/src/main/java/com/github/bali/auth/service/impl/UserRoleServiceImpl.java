package com.github.bali.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.entity.UserRole;
import com.github.bali.auth.mapper.UserRoleMapper;
import com.github.bali.auth.service.IUserRoleService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 用户角色关联信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-09
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public IPage<UserRole> page(UserRole userRole, Page<UserRole> page) {
        return this.page(page, Wrappers.lambdaQuery(userRole).orderByDesc(UserRole::getCreateTime));
    }

    @Override
    public UserRole get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(UserRole userRole) {
        userRole.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        userRole.setCreateTime(LocalDateTime.now());
        if (this.save(userRole)) {
            return userRole.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(UserRole userRole) {
        userRole.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        userRole.setModifyTime(LocalDateTime.now());
        return this.updateById(userRole);
    }

}
