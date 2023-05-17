package com.github.bali.core.framework.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 字符串工具
 * @version 1.0.0
 * @author 廖晨曦
 */
@UtilityClass
public class StringUtils {

    /**
     * 判断字符串是否为空串
     * @param str 需要判断的字符串
     * @return 为空时返回true，否则返回false
     */
    public boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }

    /**
     * 对字符串数组进行拼接
     * @param separator 拼接分隔符
     * @param array 需要拼接的字符串数组
     * @return 对字符串数组进行拼接后的结果
     */
    public String join(CharSequence separator, CharSequence... array) {
        return String.join(separator, array);
    }

    /**
     * 将传入的命名首字母改为大写
     * @param name 需要调整的命名
     * @return 调整后的命名
     */
    public String toFirstUpperCase(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        if (name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 将传入的命名首字母改为小写
     * @param name 需要调整的命名
     * @return 调整后的命名
     */
    public String toFirstLowerCase(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        if (name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * 字符串填充（填充字符'0'至原字符串前缀）
     * @param str 原字符串
     * @param length 需要填满的完整长度
     * @return 填充后的字符串
     */
    public String padding(String str, int length) {
        return padding(str, length, '0');
    }

    /**
     * 字符串填充（填充至原字符串前缀）
     * @param str 原字符串
     * @param length 需要填满的完整长度
     * @param paddingChar 填充字符
     * @return 填充后的字符串
     */
    public String padding(String str, int length, char paddingChar) {
        return padding(str, length, paddingChar, true);
    }

    /**
     * 字符串填充
     * @param str 原字符串
     * @param length 需要填满的完整长度
     * @param paddingChar 填充字符
     * @param prefix 是否填充至原字符串前缀
     * @return 填充后的字符串
     */
    public String padding(String str, int length, char paddingChar, boolean prefix) {
        StringBuilder sb = new StringBuilder();
        int paddingCount = length - str.length();
        for (int i = 0; i < paddingCount; i++) {
            sb.append(paddingChar);
        }
        if (prefix) {
            sb.append(str);
        } else {
            sb.insert(0, str);
        }
        return sb.toString();
    }

    public Set<String> marks(String text, String prefix, String postfix) {
        Set<String> markSet = new HashSet<>();
        int index = 0;
        while (true) {
            int preIndex = text.indexOf(prefix, index);
            if (preIndex < 0) {
                break;
            }
            int postIndex = text.indexOf(postfix, preIndex + prefix.length());
            if (postIndex < 0) {
                break;
            }
            String mark = text.substring(preIndex + prefix.length(), postIndex);   // 截取出的标签
            /* 检查标签中是否存在子标签，若存在则继续进行截断 */
            preIndex = mark.lastIndexOf(prefix);
            if (preIndex >= 0) {
                mark = mark.substring(preIndex + prefix.length());
            }
            if (mark.length() > 0) {
                markSet.add(mark);
            }
            index = postIndex + postfix.length();
        }
        return markSet;
    }

    public String replace(String text, Map<String, Object> table) {
        for (Map.Entry<String, Object> kv: table.entrySet()) {
            text = text.replace(kv.getKey(), kv.getValue() + "");
        }
        return text;
    }

    /**
     * 对字符串进行切割（若发生切割，则直接截断）
     * @param str 需要切割的字符串
     * @param maxLength 切割后的最大长度
     * @return 切割后的字符串
     */
    public String cut(CharSequence str, int maxLength) {
        return cut(str, maxLength, "...");
    }

    /**
     * 对字符串进行切割（若发生切割，则自动拼接省略号）
     * @param str 需要切割的字符串
     * @param maxLength 切割后的最大长度
     * @return 切割后的字符串
     */
    @SuppressWarnings("unused")
    public String cutWithEllipsis(CharSequence str, int maxLength) {
        return cut(str, maxLength, "...");
    }

    /**
     * 对字符串进行切割
     * @param str 需要切割的字符串
     * @param maxLength 切割后的最大长度
     * @param omission 切割后的省略符
     * @return 切割后的字符串
     */
    public String cut(CharSequence str, int maxLength, String omission) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str.toString();
        }
        return str.subSequence(0, maxLength - omission.length()) + omission;
    }
}
