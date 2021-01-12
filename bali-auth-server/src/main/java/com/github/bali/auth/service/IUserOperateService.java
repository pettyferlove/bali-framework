package com.github.bali.auth.service;

import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;

/**
 * @author Petty
 */
public interface IUserOperateService {

    /**
     * 获取当前登录的用户
     * @return User
     */
    User getCurrentUser();

    /**
     * 获取当前登录的用户信息
     * @return UserInfo
     */
    UserInfo getCurrentUserInfo();

}
