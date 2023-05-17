package com.github.bali.core.framework.jackson.databind;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * LocalTimeDeserializer
 * LocalTime反序列化规则
 * 示例：'10:15:30'
 * @author 刘洋
 * @version  1.0
 */
@Slf4j
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    private final DateTimeFormatter formatter;


    public LocalTimeDeserializer() {
        formatter = DatePattern.NORM_TIME_FORMATTER;
    }

    public LocalTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public LocalTimeDeserializer(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
    }

    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException {
        try {
            String string = p.getText().trim();
            if (StrUtil.isNotEmpty(string)) {
                return LocalTime.parse(string, formatter);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("日期格式数据反序列化异常", e);
            throw new RuntimeException("日期格式数据反序列化异常");
        }
    }
}
