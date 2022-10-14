package com.github.bali.core.framework.configuration;


import cn.hutool.core.date.DatePattern;
import com.google.gson.*;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Configuration
@ConditionalOnClass(Gson.class)
@AutoConfigureBefore(org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class)
public class GsonAutoConfiguration {

    final static JsonSerializer<LocalDateTime> jsonSerializerDateTime = (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DatePattern.NORM_DATETIME_FORMATTER));
    final static JsonSerializer<LocalDate> jsonSerializerDate = (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.format(DatePattern.NORM_DATE_FORMATTER));

    final static JsonSerializer<LocalTime> jsonSerializerTime = (localTime, type, jsonSerializationContext) -> new JsonPrimitive(localTime.format(DatePattern.NORM_TIME_FORMATTER));
    //反序列化
    final static JsonDeserializer<LocalDateTime> jsonDeserializerDateTime = (jsonElement, type, jsonDeserializationContext) -> LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DatePattern.NORM_DATETIME_FORMATTER);
    final static JsonDeserializer<LocalDate> jsonDeserializerDate = (jsonElement, type, jsonDeserializationContext) -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DatePattern.NORM_DATE_FORMATTER);

    final static JsonDeserializer<LocalTime> jsonDeserializerTime = (jsonElement, type, jsonDeserializationContext) -> LocalTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DatePattern.NORM_TIME_FORMATTER);

    @Bean
    @ConditionalOnMissingBean
    public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, jsonSerializerDateTime)
                .registerTypeAdapter(LocalDate.class, jsonSerializerDate)
                .registerTypeAdapter(LocalTime.class, jsonSerializerTime)
                .registerTypeAdapter(LocalDateTime.class, jsonDeserializerDateTime)
                .registerTypeAdapter(LocalDate.class, jsonDeserializerDate)
                .registerTypeAdapter(LocalTime.class, jsonDeserializerTime);
        customizers.forEach((c) -> {
            c.customize(builder);
        });
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean
    public Gson gson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }

}
