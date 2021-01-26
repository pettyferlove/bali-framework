package com.github.bali.auth.provider.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.google.common.base.Preconditions;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

import java.nio.charset.StandardCharsets;

/**
 * Token储存自定义序列化
 *
 * @author Petty
 */
public class FastJsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Preconditions.checkArgument(clazz != null,
                "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), clazz, FastJsonRedisParserConfig.DEFAULT_CONFIG);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }

        try {
            return JSON.toJSONBytes(object, SerializerFeature.WriteClassName);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return data.getBytes(StandardCharsets.UTF_8);
    }
}
