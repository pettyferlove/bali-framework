package com.github.bali.security.constants;

/**
 * @author Petty
 */

public enum UserChannelType {

    /**
     * 网站维护用户
     */
    MAINTAINER("maintainer"),

    /**
     * Web端用户
     */
    WEB("web"),
    /**
     * 移动端用户
     */
    MOBILE("mobile"),

    /**
     * 微信小程序用户
     */
    WECHAT_MINI_PROGRAM("wechat_mini_program");

    private final String type;

    UserChannelType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }

}
