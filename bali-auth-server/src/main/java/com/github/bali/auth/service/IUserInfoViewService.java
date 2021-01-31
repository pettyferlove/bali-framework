package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.domain.vo.UserInfoQueryParams;
import com.github.bali.auth.entity.UserInfoView;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-13
 */
public interface IUserInfoViewService extends IService<UserInfoView> {

    /**
     * 查询用户列表
     * @param params 查询参数
     * @param page 分页参数
     * @return 结果
     */
    IPage<UserInfoView> page(UserInfoQueryParams params, Page<UserInfoView> page);

    /**
     * 根据角色获取已绑定该角色的用户
     * @param params 查询参数
     * @param page 分页参数
     * @param role 角色名
     * @param excludeUserIds 需要排除的用户ID集合
     * @return 结果
     */
    IPage<UserInfoView> pageByRole(UserInfoQueryParams params, Page<UserInfoView> page, String role, String excludeUserIds);
}
