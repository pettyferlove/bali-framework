package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.AuthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 终端信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-09
 */
public interface IAuthClientDetailsService extends IService<AuthClientDetails> {

    /**
     * List查找
     *
     * @param authClientDetails 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<AuthClientDetails> page(AuthClientDetails authClientDetails, Page<AuthClientDetails> page);

    /**
     * 通过Id查询AuthClientDetails信息
     *
     * @param id 业务主键
     * @return 对象
     */
    AuthClientDetails get(String id);

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
      * @param authClientDetails 要创建的对象
      * @return Boolean
      */
     String create(AuthClientDetails authClientDetails);

     /**
      * 更新数据（必须带Id）
      *
      * @param authClientDetails 对象
      * @return Boolean
      */
     Boolean update(AuthClientDetails authClientDetails);

}
