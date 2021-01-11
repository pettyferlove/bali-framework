package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.entity.Tenant;

import java.util.List;

/**
 * <p>
 * 租户信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
public interface ITenantService extends IService<Tenant> {

    /**
     * List查找
     *
     * @param tenant 查询参数对象
     * @param page   Page分页对象
     * @return IPage 返回结果
     */
    IPage<Tenant> page(Tenant tenant, Page<Tenant> page);

    /**
     * 通过Id查询Tenant信息
     *
     * @param id 业务主键
     * @return 对象
     */
    Tenant get(String id);

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
     * @param tenant 要创建的对象
     * @return Boolean
     */
    String create(Tenant tenant);

    /**
     * 更新数据（必须带Id）
     *
     * @param tenant 对象
     * @return Boolean
     */
    Boolean update(Tenant tenant);


    /**
     * 获取全部租户
     *
     * @return TenantVO VO类
     */
    List<Tenant> all();


    /**
     * 创建数据
     *
     * @param userId userId
     * @param tenant 要创建的对象
     * @return Boolean
     */
    String create(String userId, Tenant tenant);

    /**
     * 更新数据（必须带Id）
     *
     * @param userId userId
     * @param tenant 对象
     * @return Boolean
     */
    Boolean update(String userId, Tenant tenant);


    /**
     * 修改状态
     *
     * @param userId userId
     * @param tenant SystemTenant
     * @return Boolean
     */
    Boolean changeStatus(String userId, Tenant tenant);

    /**
     * 检查租户是否存在
     *
     * @param tenantId 租户ID
     * @return 存在True 不存在False
     */
    Boolean check(String tenantId);

    /**
     * 检查租户当前是否有效
     *
     * @param tenantId tenantId
     * @return True 有效 False 无效
     */
    Boolean checkTenantStatus(String tenantId);

}
