package com.github.bali.auth.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pettyfer
 */
@Data
@Builder
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 用户登录用户名
     */
    private String name;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 电子邮箱
     */
    private String email;

    private String tenant;
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @Builder.Default
    private List<String> permissions = new ArrayList<>();

}
