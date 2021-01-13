package com.github.bali.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.entity.UserInfoView;

/**
 * @author Petty
 */
public interface IUserOperateService {

    /**
     * 查询用户信息视图列表
     *
     * @param userInfoView 查询参数
     * @param page         分页参数
     * @return Page
     */
    Page<UserInfoView> userInfoPage(UserInfoView userInfoView, Page<UserInfoView> page);

    /**
     * 获取当前登录的用户
     *
     * @return User
     */
    User getCurrentUser();

    /**
     * 获取当前登录的用户信息
     *
     * @return UserInfo
     */
    UserInfo getCurrentUserInfo();

}
