package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.entity.AuthClientScope;

import java.util.List;

/**
 * <p>
 * 客户端域 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-14
 */
public interface IAuthClientScopeService extends IService<AuthClientScope> {

    /**
     * List查找
     *
     * @param authClientScope 查询参数对象
     * @param page            Page分页对象
     * @return IPage 返回结果
     */
    IPage<AuthClientScope> page(AuthClientScope authClientScope, Page<AuthClientScope> page);

    /**
     * 通过Id查询AuthClientScope信息
     *
     * @param id 业务主键
     * @return 对象
     */
    AuthClientScope get(String id);

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
     * @param authClientScope 要创建的对象
     * @return Boolean
     */
    String create(AuthClientScope authClientScope);

    /**
     * 更新数据（必须带Id）
     *
     * @param authClientScope 对象
     * @return Boolean
     */
    Boolean update(AuthClientScope authClientScope);


    /**
     * 获取应用域（重写方法适配租户）
     * @return 集合
     */
    List<AuthClientScope> listScopes();

    /**
     * 批量删除客户端域
     * @param ids ID集合
     * @return 是否成功
     */
    Boolean batchDelete(String ids);
}
