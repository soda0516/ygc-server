package me.subin.commonsuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author subin
 */
@SpringBootApplication()
@MapperScan(basePackages = "me.subin.commonsuser.mapper")
public class CommonsUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonsUserApplication.class, args);
    }

}
