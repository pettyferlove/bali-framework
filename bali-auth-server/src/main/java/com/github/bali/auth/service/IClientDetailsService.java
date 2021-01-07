package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.ClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 终端信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
public interface IClientDetailsService extends IService<ClientDetails> {

    /**
     * List查找
     *
     * @param clientDetails 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<ClientDetails> page(ClientDetails clientDetails, Page<ClientDetails> page);

    /**
     * 通过Id查询ClientDetails信息
     *
     * @param id 业务主键
     * @return 对象
     */
    ClientDetails get(String id);

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
      * @param clientDetails 要创建的对象
      * @return Boolean
      */
     String create(ClientDetails clientDetails);

     /**
      * 更新数据（必须带Id）
      *
      * @param clientDetails 对象
      * @return Boolean
      */
     Boolean update(ClientDetails clientDetails);

}
