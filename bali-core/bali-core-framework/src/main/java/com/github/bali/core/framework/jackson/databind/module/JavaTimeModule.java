package com.github.bali.core.framework.jackson.databind.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.github.bali.core.framework.jackson.databind.*;

import java.time.*;

/**
 * @author Petty
 */
public class JavaTimeModule extends SimpleModule {

    private static final long serialVersionUID = -9050818405418604189L;

    public JavaTimeModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        this.addSerializer(LocalDate.class, new LocalDateSerializer());
        this.addSerializer(LocalTime.class, new LocalTimeSerializer());
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        this.addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        this.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        this.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        this.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
    }
}
