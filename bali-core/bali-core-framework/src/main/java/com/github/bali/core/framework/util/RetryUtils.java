package com.github.bali.core.framework.util;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * 重试工具
 *
 * @author 莫世源
 * @version 1.0.0
 */
@UtilityClass
public class RetryUtils {
    /**
     * 重试工具
     *
     * @param supplier 函数
     * @param retryNum 重试次数
     * @return 结果
     */
    public <T> T retry(Supplier<T> supplier, int retryNum) {
        T obj;
        int num = 0;
        while (true) {
            num++;
            try {
                obj = supplier.get();
                break;
            } catch (Exception e) {
                if (num >= retryNum) {
                    throw e;
                }
            }
        }
        return obj;
    }
}
