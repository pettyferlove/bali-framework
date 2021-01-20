package com.github.bali.auth.service;

import com.github.bali.auth.domain.dto.BasicAuth;
import com.github.bali.auth.domain.vo.WeChatUserRegister;

/**
 * @author Pettyfer
 */
public interface IRegisterService {

    /**
     * 微信用户注册
     *
     * @param register 注册信息
     * @param auth 租户ID
     * @param type     类型
     * @return 是否成功
     */
    Boolean registerWeChat(WeChatUserRegister register, BasicAuth auth, String type);

}
