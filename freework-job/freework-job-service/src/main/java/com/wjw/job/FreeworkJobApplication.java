package com.wjw.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wjw
 * @date 2021/1/21 1:58
 */
@SpringBootApplication
@MapperScan("com.wjw.job.mapper")
public class FreeworkJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkJobApplication.class, args);
    }
}
