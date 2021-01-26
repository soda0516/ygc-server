package me.subin.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author bin
 * @ClassName ZeroToNullSerializer
 * @Description TODO
 * @date 2021/1/10 15:01
 */
@JacksonStdImpl
public class ZeroToNullSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o instanceof Short) {
            if (((Short) o).compareTo((short)0) == 0) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber((Short) o);
            }
        }
        if (o instanceof Integer) {
            if ((Integer) o == 0) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber((Integer) o);
            }
        }
        if (o instanceof Float) {
            if (((Float) o).compareTo(0f) == 0) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber((Float) o);
            }
        }

        if (o instanceof Double) {
            if (((Double) o).compareTo(0D) == 0) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber((Double) o);
            }
        }

        if (o instanceof Long) {
            if (((Long) o).compareTo(0L) == 0) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber((Long) o);
            }
        }
        if (o instanceof BigDecimal) {
            if (((BigDecimal) o).compareTo(BigDecimal.ZERO) == 0) {
                jsonGenerator.writeNull();
            }else {
                jsonGenerator.writeNumber((BigDecimal) o);
            }
        }
    }
}
