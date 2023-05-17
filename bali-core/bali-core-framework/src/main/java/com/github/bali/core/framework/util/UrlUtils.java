package com.github.bali.core.framework.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.URLUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * URL工具类
 * @author 莫世源
 * @version 1.0.0
 */
@Slf4j
@UtilityClass
public class UrlUtils {
    /**
     * 将通配符表达式转化为正则表达式
     *
     * @param path 路径
     * @return 正则
     */

    public String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for (int i = 0; i < len; i++) {

            if (chars[i] == '*') {
                //遇到*字符
                if (preX) {
                    //如果是第二次遇到*，则将**替换成.*
                    sb.append(".*");
                    preX = false;

                } else if (i + 1 == len) {
                    //如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                    sb.append("[^/]*");
                } else {
                    //否则单星后面还有字符，则不做任何动作，下一把再做动作
                    preX = true;
                }
            } else {
                //遇到非*字符
                if (preX) {
                    //如果上一把是*，则先把上一把的*对应的[^/]*添进来
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?') {
                    //接着判断当前字符是不是?，是的话替换成.
                    sb.append('.');
                } else {
                    //不是?的话，则就是普通字符，直接添进来
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }


    /**
     * 通配符模式
     *
     * @param excludePath - 不过滤地址
     * @param reqUrl      - 请求地址
     * @return 校验结果
     */

    public boolean filterUrls(String excludePath, String reqUrl) {
        String regPath = getRegPath(excludePath);
        return ReUtil.isMatch(regPath, reqUrl);
    }

    /**
     * 检验是否在非过滤地址
     *
     * @param excludeUrls 不过滤地址
     * @param reqUrl      请求地址
     * @return 校验结果
     */
    public boolean checkWhiteList(String[] excludeUrls, String reqUrl) {
        for (String url : excludeUrls) {
            if (filterUrls(url, reqUrl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 需要转换的内容（ASCII码形式之外的内容），用十六进制表示法转换出来，并在之前加上%开头
     *
     * @param url url
     * @return 转码后的url
     */
    public String encode(String url) {
        return URLUtil.encode(url);
    }

    /**
     * 将%开头的16进制表示的内容解码
     *
     * @param url url
     * @return 解码后的url
     */
    public String decode(String url) {
        return URLUtil.decode(url);
    }

    /**
     * 获得path部分 URI -> http://www.aaa.bbb/search?scope=ccc&q=ddd PATH -> /search
     *
     * @param url url
     * @return page
     */
    public String getPage(String url) {
        return URLUtil.getPath(url);
    }

    /**
     * Controller占位符转过滤器支持格式
     * 例子 /gzrs-external/rest/es/v1/user/{iad}/reset/{dfsd} -> /gzrs-external/rest/es/v1/user/*\/reset/*
     *
     * @param url url
     * @return 转义
     */
    public String escapePlaceholder(String url) {
        return ReUtil.replaceAll(url, "\\{((?!/).)*\\}", "*");
    }
}
