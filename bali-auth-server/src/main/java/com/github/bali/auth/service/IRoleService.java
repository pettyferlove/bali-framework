package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.entity.Role;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
public interface IRoleService extends IService<Role> {

    /**
     * List查找
     *
     * @param role 查询参数对象
     * @param page Page分页对象
     * @return IPage 返回结果
     */
    IPage<Role> page(Role role, Page<Role> page);

    /**
     * 通过Id查询Role信息
     *
     * @param id 业务主键
     * @return 对象
     */
    Role get(String id);

    /**
     * 通过Id删除信息
     *
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 创建数据
     *
     * @param role 要创建的对象
     * @return Boolean
     */
    String create(Role role);

    /**
     * 更新数据（必须带Id）
     *
     * @param role 对象
     * @return Boolean
     */
    Boolean update(Role role);


    /**
     * 批量删除角色
     * @param ids ID集合
     * @return Boolean
     */
    Boolean batchDelete(String ids);
}
