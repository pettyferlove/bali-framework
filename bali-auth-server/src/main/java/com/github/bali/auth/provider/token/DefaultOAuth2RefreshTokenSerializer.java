package com.github.bali.auth.provider.token;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;

import java.lang.reflect.Type;

/**
 * @author Pettyfer
 */
@SuppressWarnings("ALL")
public class DefaultOAuth2RefreshTokenSerializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == DefaultOAuth2RefreshToken.class) {
            JSONObject jsonObject = parser.parseObject();
            String tokenId = jsonObject.getString("value");
            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(tokenId);
            return (T) refreshToken;
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
