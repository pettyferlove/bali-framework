package com.github.bali.core.framework.util;

import lombok.experimental.UtilityClass;

/**
 * 散列工具
 * @version 1.0.0
 * @author 廖晨曦
 */
@UtilityClass
public class HashUtils {

    private final char[] HASH62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private final char[] HASH36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final char[] HASH10 = "0123456789".toCharArray();

    /**
     * 散列至62个字符
     *
     * @param str 待散列内容
     * @return 散列结果
     */
    public char hash62(String str) {
        return hash(str, HASH62);
    }

    /**
     * 散列至36个字符
     *
     * @param str 待散列内容
     * @return 散列结果
     */
    public char hash36(String str) {
        return hash(str, HASH36);
    }

    /**
     * 散列至10个字符
     *
     * @param str 待散列内容
     * @return 散列结果
     */
    public char hash10(String str) {
        return hash(str, HASH10);
    }

    /**
     * 散列至指定内容
     *
     * @param str        待散列内容
     * @param hashValues 散列结果集合
     * @return 散列结果
     */
    public char hash(String str, char[] hashValues) {
        return hashValues[hash(str, hashValues.length)];
    }

    /**
     * 散列至指定容量
     *
     * @param str        待散列内容
     * @param hashVolume 散列容量
     * @return 散列结果
     */
    public int hash(String str, int hashVolume) {
        char[] chars = str.toCharArray();
        long sum = 0;
        for (char c : chars) {
            sum += c;
        }
        return hash(sum, hashVolume);
    }

    /**
     * 散列至62个字符
     *
     * @param value 待散列内容
     * @return 散列结果
     */
    public char hash62(long value) {
        return hash(value, HASH62);
    }

    /**
     * 散列至36个字符
     *
     * @param value 待散列内容
     * @return 散列结果
     */
    public char hash36(long value) {
        return hash(value, HASH36);
    }

    /**
     * 散列至10个字符
     *
     * @param value 待散列内容
     * @return 散列结果
     */
    public char hash10(long value) {
        return hash(value, HASH10);
    }

    /**
     * 散列至指定内容
     *
     * @param value      待散列内容
     * @param hashValues 散列结果集合
     * @return 散列结果
     */
    public char hash(long value, char[] hashValues) {
        return hashValues[hash(value, hashValues.length)];
    }

    /**
     * 散列至指定容量
     *
     * @param value      待散列内容
     * @param hashVolume 散列容量
     * @return 散列结果
     */
    public int hash(long value, int hashVolume) {
        return (int) (value % hashVolume);
    }
}
