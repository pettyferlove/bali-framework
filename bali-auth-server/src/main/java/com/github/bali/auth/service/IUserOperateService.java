package com.github.bali.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.UserOperate;
import com.github.bali.auth.domain.vo.UserRoleVO;
import com.github.bali.auth.entity.User;
import com.github.bali.auth.entity.UserInfo;
import com.github.bali.auth.entity.UserInfoView;

import java.util.List;

/**
 * @author Petty
 */
public interface IUserOperateService {

    UserRoleVO loadUserRole(String userId);
    List<String> loadUserRoleIds(String userId);

    /**
     * 查询用户信息视图列表
     *
     * @param userInfoView 查询参数
     * @param page         分页参数
     * @return Page
     */
    Page<UserInfoView> userInfoPage(UserInfoView userInfoView, Page<UserInfoView> page);

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return User
     */
    User getUser(String userId);

    /**
     * 根据用户ID获取用户信息详情
     * @param userId 用户ID
     * @return UserInfo
     */
    UserInfo getUserInfo(String userId);

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

    /**
     * 新增用户
     * @param userOperate 用户信息
     * @return 新增之后的用户ID
     */
    String create(UserOperate userOperate);

    /**
     * 更新用户信息
     * @param userOperate 用户信息
     * @return 是否成功
     */
    Boolean update(UserOperate userOperate);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    Boolean delete(String id);

    /**
     * 更新用户角色
     * @param userId 用户ID
     * @param roleIds 角色ID
     * @return 是否成功
     */
    Boolean updateUserRole(String userId, String roleIds);
}
