package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.domain.vo.TenantDictVO;
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
     * 获取租户字典
     * @return 全部租户集合
     */
    List<TenantDictVO> dict();
}
