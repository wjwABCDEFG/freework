package com.wjw.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wjw
 * @date 2021/1/21 1:58
 */
@SpringBootApplication
@MapperScan("com.wjw.job.mapper")
@ComponentScan(basePackages = {"com.wjw"})
public class FreeworkJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkJobApplication.class, args);
    }
}
