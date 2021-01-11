package com.github.bali.auth.provider.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Preconditions;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * Token储存自定义序列化
 *
 * @author Petty
 */
public class FastJsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    private static final ParserConfig CONFIG = ParserConfig.getGlobalInstance();

    static {
        init();
    }

    protected static void init() {
        //自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        CONFIG.setAutoTypeSupport(true);
        CONFIG.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOAuth2RefreshTokenDeserializer());
        CONFIG.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationDeserializer());
        CONFIG.putDeserializer(AuthorizationRequest.class, new ObjectDeserializer() {
            @Override
            public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
                return null;
            }

            @Override
            public int getFastMatchToken() {
                return 0;
            }
        });
        CONFIG.addAccept("org.springframework.security.oauth2.provider.");
        CONFIG.addAccept("org.springframework.security.oauth2.provider.client");

        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication",
                OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails",
                BaseClientDetails.class);
        TypeUtils.addMapping("org.springframework.security.web.savedrequest.DefaultSavedRequest",
                DefaultSavedRequest.class);
        TypeUtils.addMapping("org.springframework.security.authentication.InternalAuthenticationServiceException",
                InternalAuthenticationServiceException.class);
        TypeUtils.addMapping("org.springframework.security.authentication.BadCredentialsException",
                BadCredentialsException.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.AuthorizationRequest",
                AuthorizationRequest.class);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Preconditions.checkArgument(clazz != null,
                "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), clazz, CONFIG);
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
            return JSON.toJSONBytes(object, SerializerFeature.WriteClassName,
                    SerializerFeature.DisableCircularReferenceDetect);
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
