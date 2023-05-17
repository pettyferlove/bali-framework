package com.github.bali.core.framework.jackson.databind;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * LocalTimeSerializer
 * LocalTime序列化规则
 * 示例：'10:15:30'
 * @author 刘洋
 * @version  1.0
 */
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    private final DateTimeFormatter formatter;

    public LocalTimeSerializer() {
        formatter = DatePattern.NORM_TIME_FORMATTER;
    }

    public LocalTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public LocalTimeSerializer(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeString(value.format(formatter));
    }


}
