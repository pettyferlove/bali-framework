package com.github.bali.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bali.auth.domain.dto.LoginAttempts;
import com.github.bali.auth.domain.vo.ChangePassword;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.mapper.UserMapper;
import com.github.bali.auth.properties.AuthorizeProperties;
import com.github.bali.auth.service.IUserService;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.constants.SecurityConstant;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

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

    private final AuthorizeProperties properties;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AuthorizeProperties properties, PasswordEncoder passwordEncoder) {
        this.properties = properties;
        this.passwordEncoder = passwordEncoder;
    }

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

    @Override
    public Boolean changePassword(ChangePassword changePassword) {
        if (!changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
            throw new BaseRuntimeException("两次输入的密码不一致");
        } else {
            Optional<User> systemUserOptional = Optional.ofNullable(this.getById(SecurityUtil.getUser().getId()));
            if (systemUserOptional.isPresent()) {
                User user = systemUserOptional.get();
                if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
                    throw new BaseRuntimeException("原始密码错误");
                } else {
                    user.setId(SecurityUtil.getUser().getId());
                    user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
                    user.setModifier(SecurityUtil.getUser().getId());
                    user.setModifyTime(LocalDateTime.now());
                    return this.update(user);
                }
            }

        }
        throw new BaseRuntimeException("密码修改失败");
    }

    @Override
    public LoginAttempts getLoginAttempts(String username) {
        return baseMapper.getLoginAttempts(username);
    }

    @Override
    public void loginFailed(String username) {
        LoginAttempts loginAttempts = this.getLoginAttempts(username);
        AuthorizeProperties.LoginAttempt loginAttemptConfig = properties.getLoginAttempt();
        if (loginAttempts != null) {
            if (ObjectUtil.isNotNull(loginAttempts.getLastLoginFailTime())) {
                if (loginAttempts.getLastLoginFailTime().isAfter(LocalDateTime.now().minusSeconds(loginAttemptConfig.getLockingDuration()/1000))) {
                    baseMapper.loginFailed(username, loginAttempts.getLoginFailNum() + 1);
                } else {
                    baseMapper.loginFailed(username, 1);
                }
            } else {
                baseMapper.loginFailed(username, 1);
            }
            if (loginAttempts.getLoginFailNum() + 1 >= loginAttemptConfig.getTimesBeforeLock()) {
                baseMapper.lockUser(username);
            }
        }
    }

    @Override
    public void checkLoginAttempts(User user) {
        LoginAttempts loginAttempts = this.getLoginAttempts(user.getLoginId());
        AuthorizeProperties.LoginAttempt loginAttemptConfig = properties.getLoginAttempt();
        if (loginAttemptConfig.getEnable()) {
            if ((SecurityConstant.STATUS_LOCK == user.getStatus() && LocalDateTime.now().isAfter(loginAttempts.getLastLoginFailTime().plus(loginAttemptConfig.getLockingDuration(), ChronoUnit.MILLIS)))) {
                user.setStatus(SecurityConstant.STATUS_NORMAL);
                this.loginSucceeded(user.getLoginId());
            }
        }
    }

    @Override
    public void loginSucceeded(String username) {
        baseMapper.loginSucceeded(username);
    }

}
