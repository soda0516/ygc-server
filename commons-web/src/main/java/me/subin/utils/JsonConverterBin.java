package me.subin.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import me.subin.exception.JsonConverterException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Describe
 * @Author orang
 * @Create 2019/4/16 16:03
 **/


public final class JsonConverterBin {

    private static ObjectMapper jacksonObjectMapper;

    static {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        jacksonObjectMapper = new ObjectMapper();
        //序列化的时候序列对象的所有属性
        jacksonObjectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        jacksonObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        jacksonObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        jacksonObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        jacksonObjectMapper.registerModule(module);
    }

    /**
     * 将一个对象序列化成一个字符串
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String transferToJson(T t){
        if (Objects.isNull(t)){
            return null;
        }
        try {
            return jacksonObjectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new JsonConverterException(e);
        }
    }

    /**
     * 讲一个json字符串反序列化成一个对象
     * @param s
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T transferToObject(String s,Class<T> tClass){
        if (StringUtils.isBlank(s)){
            s = "{}";
        }
        try {
            return jacksonObjectMapper.readValue(s,tClass);
        } catch (IOException e) {
            throw new JsonConverterException(e);
        }
    }


    /**
     * 转换一个字符串为对象List容器
     * @param s
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> transferToObjectList(String s,Class<T> tClass){
        if (StringUtils.isBlank(s)){
            s = "[]";
        }
        try {
            JavaType javaType = jacksonObjectMapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
            return jacksonObjectMapper.readValue(s,javaType);
        } catch (IOException e) {
            throw new JsonConverterException(e);
        }
    }
}
