package com.github.bali.auth.provider.service.impl;

import com.github.bali.auth.provider.service.AbstractCaptchaValidateService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 默认验证码处理
 *
 * @author Petty
 */
@Service("captchaValidateService")
public class CaptchaValidateServiceImpl extends AbstractCaptchaValidateService {

    private final RedisTemplate<String, String> redisTemplate;

    public CaptchaValidateServiceImpl(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean create(String code, String captcha, long timeout) {
        boolean result = false;
        StringBuilder stringBuffer = new StringBuilder("captcha-code-key");
        stringBuffer.append(":");
        stringBuffer.append(code);
        try {
            redisTemplate.opsForValue().set(stringBuffer.toString(), captcha, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stringBuffer.setLength(0);
        }
        return result;

    }

    @Override
    public Boolean validate(String code, String captcha) {
        return super.validate("captcha-code-key" ,code, captcha);
    }
}
