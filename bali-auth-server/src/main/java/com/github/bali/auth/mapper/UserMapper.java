package com.github.bali.auth.mapper;

import com.github.bali.auth.domain.dto.LoginAttempts;
import com.github.bali.auth.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户注册信息 Mapper 接口
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
public interface UserMapper extends BaseMapper<User> {

    LoginAttempts getLoginAttempts(String username);

    void loginFailed(String username, Integer failNum);

    void lockUser(String username);

    void loginSucceeded(String username);

}
