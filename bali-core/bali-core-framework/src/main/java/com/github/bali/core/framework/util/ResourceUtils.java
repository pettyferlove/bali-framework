package com.github.bali.core.framework.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源工具
 * @version 1.0.0
 * @author 廖晨曦
 */
@SuppressWarnings("unused")
@UtilityClass
public class ResourceUtils {

    /**
     * 从本地classpath获取资源流
     * @param resourceLocation 本地classpath资源名，路径使用“/”分隔
     * @return 资源内容
     * @throws IOException 读取资源流失败
     */
    public InputStream inputStreamFromLocal(String resourceLocation) throws IOException {
        return org.springframework.util.ResourceUtils.getURL(
                org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX + resourceLocation).openStream();
    }
    /**
     * 从本地classpath获取资源内容
     * @param resourceLocation 本地classpath资源名，路径使用“/”分隔
     * @return 资源内容
     * @throws IOException 读取资源流失败
     */
    public byte[] bytesFromLocal(String resourceLocation) throws IOException {
        try (InputStream is = inputStreamFromLocal(resourceLocation)) {
            return StreamUtils.copyToByteArray(is);
        }
    }
}
