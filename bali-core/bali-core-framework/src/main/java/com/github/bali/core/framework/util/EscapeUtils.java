package com.github.bali.core.framework.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.HtmlUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 转码工具
 * @author Petty
 * @version 1.0.0
 */
@UtilityClass
@Slf4j
public class EscapeUtils {

    /**
     * XSS转码（利用反射），只对String类型转义
     * @param obj 对象
     * @return 转码后的str
     */
    public Object htmlEscapeByReflect(Object obj) {
        return escapeByReflect(obj,true);
    }

    /**
     * XSS解码（利用反射），只对String类型解码
     * @param obj 对象
     * @return 解码后的str
     */
    public Object htmlUnescapeByReflect(Object obj) {
        return escapeByReflect(obj,false);
    }

    /**
     * XSS转义（利用反射），只对String类型转义
     * @param obj 对象
     * @param escape true转义 false转回去
     */
    private Object escapeByReflect(Object obj,boolean escape){
        Object rtnObj;
        try {
            if (obj ==null) {
                rtnObj = null;
            } else if (obj instanceof String) {
                rtnObj = escape((String) obj,escape);
            }else if (isIgnored(obj.getClass())) {
                rtnObj = obj;
            } else if (obj instanceof List) {
                List list=(List) obj;
                if(!list.isEmpty()){
                    for(int i=0;i< list.size();i++){
                        list.set(i,escapeByReflect(list.get(i),escape));
                    }
                }
                rtnObj=list;
            } else if (obj instanceof Set) {
                Set set=(Set) obj;
                if(!set.isEmpty()){
                    Set rtn=new HashSet(set.size());
                    set.forEach(key->rtn.add(escapeByReflect(key,escape)));
                    rtnObj=rtn;
                }else{
                    rtnObj= Collections.EMPTY_SET;
                }
            } else if (obj instanceof Map) {
                Map map=(Map) obj;
                if(!map.isEmpty()){
                    Map rtn=new HashMap(map.size());
                    map.forEach((key, value) -> rtn.put(key,escapeByReflect(value,escape)));
                    rtnObj=rtn;
                }else{
                    rtnObj=Collections.EMPTY_MAP;
                }
            } else {
                Field[] fields = getAllFields(obj);
                for(int i=0; i<fields.length; i++){
                    Field f = fields[i];
                    f.setAccessible(true);
                    if(String.class.isAssignableFrom(f.getType())){
                        String str= (String) f.get(obj);
                        String escapeStr=escape(str,escape);
                        f.set(obj,escapeStr);
                    }else if (isIgnored(f.getType())) {
                        //忽略的属性，不用处理
                    }else{
                        f.set(obj,escapeByReflect(f.get(obj),escape));
                    }
                }
                rtnObj=obj;
            }
        } catch (IllegalAccessException e) {
            log.error("XSS转义失败",e);
            throw new RuntimeException(e.getMessage());
        }
        return rtnObj;
    }
    private boolean isIgnored(Class<?> type) {
        return (type.isPrimitive()
                || Byte.class.isAssignableFrom(type)
                || Short.class.isAssignableFrom(type)
                || Integer.class.isAssignableFrom(type)
                || Long.class.isAssignableFrom(type)
                || Float.class.isAssignableFrom(type)
                || Double.class.isAssignableFrom(type)
                || Boolean.class.isAssignableFrom(type)
                || Date.class.isAssignableFrom(type)
                || LocalDate.class.isAssignableFrom(type)
                || LocalTime.class.isAssignableFrom(type)
                || LocalDateTime.class.isAssignableFrom(type)
                || Enum.class.isAssignableFrom(type));
    }

    /**
     * 转义
     * @param str 转码字符串
     * @param escape true转义 false转回去
     * @return 转义
     */
    private String escape(String str,boolean escape){
        if(escape){
            return HtmlUtils.htmlEscape(str);
        }else{
            return HtmlUtils.htmlUnescape(str);
        }
    }
    /**
     * 获取所有的属性，包括父类
     * @param object 对象
     * @return 所有的属性
     */
    private Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != Object.class) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
