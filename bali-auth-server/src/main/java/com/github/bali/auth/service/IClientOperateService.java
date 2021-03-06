package com.github.bali.auth.service;

import com.github.bali.auth.domain.vo.ClientCreateResponseVO;
import com.github.bali.auth.domain.vo.ClientDetailsVO;
import com.github.bali.auth.entity.AuthClientDetails;

import java.util.List;

/**
 * @author Petty
 */
public interface IClientOperateService {


    /**
     * 获取应用已选的Scope
     * @param id 应用ID
     * @return 集合
     */
    List<String> selectedScope(String id);

    /**
     * 创建应用
     * @param details 应用详情
     * @return ID
     */
    ClientCreateResponseVO create(ClientDetailsVO details);

    /**
     * 更新应用
     * @param details 应用详情
     * @return Boolean
     */
    Boolean update(ClientDetailsVO details);

    /**
     * 根据ID删除应用
     * @param id ID
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 批量删除客户端
     * @param ids ID集合
     * @return 是否成功
     */
    Boolean batchDelete(String ids);

    AuthClientDetails viewSecret(String id);
}
