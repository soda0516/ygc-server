package me.subin.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author : hongqiangren.
 * @since: 2018/10/30 23:10
 */
@JacksonStdImpl
public class NullToZeroDeserializer extends JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Class<? extends NullToZeroDeserializer> aClass1 = this.getClass();
        JsonToken currentToken = p.getCurrentToken();
        Class<? extends JsonParser> aClass = p.getClass();
        Class<? extends JsonParser> abClass = p.getClass();
        return 0;
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        System.out.println("测试");
        return super.getEmptyValue(ctxt);
    }
}
