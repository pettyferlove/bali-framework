package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户注册信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
public interface IUserService extends IService<User> {

    /**
     * List查找
     *
     * @param user 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<User> page(User user, Page<User> page);

    /**
     * 通过Id查询User信息
     *
     * @param id 业务主键
     * @return 对象
     */
    User get(String id);

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
      * @param user 要创建的对象
      * @return Boolean
      */
     String create(User user);

     /**
      * 更新数据（必须带Id）
      *
      * @param user 对象
      * @return Boolean
      */
     Boolean update(User user);

}
