package com.github.bali.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bali.auth.domain.vo.PersonalDetails;
import com.github.bali.auth.entity.UserInfo;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2021-01-07
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * List查找
     *
     * @param userInfo 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<UserInfo> page(UserInfo userInfo, Page<UserInfo> page);

    /**
     * 通过Id查询UserInfo信息
     *
     * @param id 业务主键
     * @return 对象
     */
    UserInfo get(String id);

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
     * @param userInfo 要创建的对象
     * @return Boolean
     */
    String create(UserInfo userInfo);

    /**
     * 更新数据（必须带Id）
     *
     * @param userInfo 对象
     * @return Boolean
     */
    Boolean update(UserInfo userInfo);

    /**
     * 获取个人信息详情
     *
     * @return PersonalDetails
     */
    PersonalDetails getDetails();

    /**
     * 更新个人信息详情
     *
     * @param details PersonalDetails
     * @return 是否成功
     */
    Boolean updateDetails(PersonalDetails details);
}
