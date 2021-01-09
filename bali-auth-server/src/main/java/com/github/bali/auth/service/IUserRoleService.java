package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色关联信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-09
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * List查找
     *
     * @param userRole 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<UserRole> page(UserRole userRole, Page<UserRole> page);

    /**
     * 通过Id查询UserRole信息
     *
     * @param id 业务主键
     * @return 对象
     */
    UserRole get(String id);

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
      * @param userRole 要创建的对象
      * @return Boolean
      */
     String create(UserRole userRole);

     /**
      * 更新数据（必须带Id）
      *
      * @param userRole 对象
      * @return Boolean
      */
     Boolean update(UserRole userRole);

}
