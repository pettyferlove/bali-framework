package com.github.bali.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.mapper.UserMapper;
import com.github.bali.auth.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.auth.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 用户注册信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public IPage<User> page(User user, Page<User> page) {
        return this.page(page, Wrappers.lambdaQuery(user).orderByDesc(User::getCreateTime));
    }

    @Override
    public User get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(User user) {
        user.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        user.setCreateTime(LocalDateTime.now());
        if (this.save(user)) {
            return user.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(User user) {
        user.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getId());
        user.setModifyTime(LocalDateTime.now());
        return this.updateById(user);
    }

}
