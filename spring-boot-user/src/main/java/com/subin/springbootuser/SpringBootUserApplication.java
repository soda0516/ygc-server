package com.subin.springbootuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.subin.springbootcore","com.subin.springbootuser"})
@EnableSwagger2
@MapperScan(basePackages = "com.subin.springbootuser.mapper")
public class SpringBootUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootUserApplication.class, args);
    }

}
