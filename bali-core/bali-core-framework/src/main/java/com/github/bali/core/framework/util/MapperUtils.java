package com.github.bali.core.framework.util;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.github.bali.core.framework.jackson.databind.module.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 映射工具
 * @author Petty
 * @version 1.0.0
 */
@UtilityClass
public class MapperUtils {

	public Map<String, Object> object2Map(Object obj) {
		try {
			return object2Map(obj, defaultMapper());
		} catch (Exception e) {
			throw new IllegalArgumentException("不支持的类型: " + obj.getClass());
		}
	}
	public Map<String, Object> object2Map(Object obj, ObjectMapper mapper) throws Exception {
		if (obj == null) {
			return Collections.emptyMap();
		}
		Map<String, Object> map = new HashMap<>();
		if (obj instanceof Map) {
			Map<?, ?> objMap = (Map<?, ?>) obj;
			for (Map.Entry<?, ?> entry: objMap.entrySet()) {
				if (entry.getKey() != null) {
					map.put(entry.getKey().toString(), entry.getValue());
				}
			}
		} else {
			SerializerProvider provider = mapper.getSerializerProviderInstance();
			JsonSerializer<Object> serializer = provider.findTypedValueSerializer(obj.getClass(), true, null);
			Iterator<PropertyWriter> it = serializer.properties();

			while (it.hasNext()) {
				PropertyWriter pw = it.next();
				if (pw instanceof BeanPropertyWriter) {
					BeanPropertyWriter bpw = (BeanPropertyWriter) pw;
					Object value = bpw.get(obj);
					map.put(bpw.getName(), value);
				} else if (pw != null) {
					throw new UnsupportedOperationException(pw.getClass().toString());
				}
			}
		}
		return map;
	}

	public ObjectMapper defaultMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

}
