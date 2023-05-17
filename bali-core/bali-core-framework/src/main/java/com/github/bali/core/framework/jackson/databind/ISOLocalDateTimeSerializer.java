package com.github.bali.core.framework.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * ISOLocalDateTimeSerializer
 * LocalDateTime序列化规则（ISO）
 * 示例：'2011-12-03T10:15:30'
 *
 * @author 刘洋
 * @version 1.0
 */
public class ISOLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private final DateTimeFormatter formatter;

    public ISOLocalDateTimeSerializer() {
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public ISOLocalDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public ISOLocalDateTimeSerializer(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
    }

    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     */
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(formatter));
    }
}
