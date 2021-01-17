package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.entity.RoleView;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-17
 */
public interface IRoleViewService extends IService<RoleView> {

    /**
     * 分页查询角色列表
     * @param role 角色
     * @param roleName 角色名
     * @param page 分页参数
     * @return 查询结果
     */
    IPage<RoleView> page(String role, String roleName, Page<RoleView> page);
}
