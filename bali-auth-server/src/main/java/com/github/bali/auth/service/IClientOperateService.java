package com.github.bali.auth.service;

import com.github.bali.auth.domain.vo.ClientCreateResponseVO;
import com.github.bali.auth.domain.vo.ClientDetailsVO;

/**
 * @author Petty
 */
public interface IClientOperateService {


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
}
