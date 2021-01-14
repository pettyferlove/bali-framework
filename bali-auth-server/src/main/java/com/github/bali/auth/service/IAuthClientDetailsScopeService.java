package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.AuthClientDetailsScope;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-14
 */
public interface IAuthClientDetailsScopeService extends IService<AuthClientDetailsScope> {

    /**
     * List查找
     *
     * @param authClientDetailsScope 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<AuthClientDetailsScope> page(AuthClientDetailsScope authClientDetailsScope, Page<AuthClientDetailsScope> page);

    /**
     * 通过Id查询AuthClientDetailsScope信息
     *
     * @param id 业务主键
     * @return 对象
     */
    AuthClientDetailsScope get(String id);

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
      * @param authClientDetailsScope 要创建的对象
      * @return Boolean
      */
     String create(AuthClientDetailsScope authClientDetailsScope);

     /**
      * 更新数据（必须带Id）
      *
      * @param authClientDetailsScope 对象
      * @return Boolean
      */
     Boolean update(AuthClientDetailsScope authClientDetailsScope);

}
