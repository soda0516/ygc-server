package com.bin.serverapi.base.config;

import me.subin.converter.CustomNumberNullToZeroConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author bin
 * @ClassName CustomBean
 * @Description TODO
 * @date 2021/1/25 22:44
 */
@Component
public class CustomBean {
    @Bean
    public CustomNumberNullToZeroConverter customNumberNullToZeroConverter() {
        return new CustomNumberNullToZeroConverter();
    }
}
