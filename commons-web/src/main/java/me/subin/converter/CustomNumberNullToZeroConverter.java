package me.subin.converter;

import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author bin
 * @ClassName CustomConverter
 * @Description TODO
 * @date 2021/1/25 22:10
 */
public class CustomNumberNullToZeroConverter implements Converter {
    @Override
    public Object convert(Object o, Class aClass, Object o1) {
            if (String.class.equals(aClass)){
                if (Objects.isNull(o)){
                    return "";
                }else {
                    return o ;
                }
            } else if (Short.class.equals(aClass) && !"setId".equals(o1)){
                if (Objects.isNull(o)){
                    return 0;
                }else {
                    return o ;
                }
            } else if (Integer.class.equals(aClass) && !"setId".equals(o1)){
                if (Objects.isNull(o)){
                    return 0;
                }else {
                    return o;
                }
            } else if (Long.class.equals(aClass) && !"setId".equals(o1)){
                if (Objects.isNull(o)){
                    return 0L;
                }else {
                    return o ;
                }
            } else if (Float.class.equals(aClass)){
                if (Objects.isNull(o)){
                    return 0F;
                }else {
                    return o ;
                }
            } else if (Double.class.equals(aClass)){
                if (Objects.isNull(o)){
                    return 0D;
                }else {
                    return o ;
                }
            } else if (BigInteger.class.equals(aClass)){
                if (Objects.isNull(o)){
                    return BigInteger.ZERO;
                }else {
                    return o ;
                }
            } else if (BigDecimal.class.equals(aClass)){
                if (Objects.isNull(o)){
                    return BigDecimal.ZERO;
                }else {
                    return o ;
                }
            } else {
                return o;
            }
    }
}
